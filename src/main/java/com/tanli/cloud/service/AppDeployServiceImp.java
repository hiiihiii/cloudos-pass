package com.tanli.cloud.service;

import com.tanli.cloud.constant.SystemConst;
import com.tanli.cloud.dao.*;
import com.tanli.cloud.model.*;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.K8sClient;
import com.tanli.cloud.utils.UuidUtil;
import io.fabric8.kubernetes.api.model.NodeAddress;
import io.fabric8.kubernetes.api.model.ReplicationController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppDeployServiceImp implements AppDeployService {

    @Autowired
    private DeploymentDao deploymentDao;
    @Autowired
    private K8sRcDao k8sRcDao;
    @Autowired
    private K8sServiceDao k8sServiceDao;
    @Autowired
    private K8sPodDao k8sPodDao;
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private ImageInfoDao imageInfoDao;
    @Autowired
    private UserLogDao userLogDao;

    private static final Logger LOGGE = LoggerFactory.getLogger(AppDeployServiceImp.class);

    @Override
    public APIResponse deployImage(User user, DeployedImage deployedImage) {
        JSONObject jsonObject=JSONObject.fromObject(deployedImage.getContainer());
        DeployContainer deployContainer = (DeployContainer) JSONObject.toBean(jsonObject, DeployContainer.class);
        ImageInfo imageInfo = imageInfoDao.getImagesAll(user.getUser_uuid())
                .stream()
                .filter(imageInfo1 -> imageInfo1.getApp_id().equals(deployedImage.getApp_id()))
                .findFirst().orElse(null);
        //修改镜像部署次数
        int deploycount = imageInfo.getDeploycount() + 1;
        imageInfoDao.updateDeployCount(imageInfo.getApp_id(), deploycount);
        //将数据保存进tl_deployment表中
        Deployment deployment = saveDeployment(deployedImage, user.getUser_uuid());
        if(deployment != null) {
            K8sClient k8sClient = new K8sClient();
            // 创建RC
            ReplicationController replicationController = k8sClient.createRC(deployedImage, deployContainer);
            // 创建Service
            io.fabric8.kubernetes.api.model.Service service = k8sClient.createService(deployedImage, deployContainer);

            K8s_Rc rc = saveRc(deployedImage, deployment, service, replicationController);
            K8s_Service svc = saveService(deployedImage, deployment, service) ;

            //添加用户日志
            DateTime now = DateTime.now();
            String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
            UserLog userLog = new UserLog (
                    UuidUtil.getUUID(),
                    user.getUser_uuid(),
                    user.getUserName(),
                    SystemConst.APPLICATION,
                    deployment.getDeploy_uuid(),
                    "创建应用" + deployment.getDeploy_name(),
                    "0",
                    nowStr );
            userLogDao.addUserLog(userLog);
            LOGGE.info("[AppDeployServiceImp Info]: " + "部署" + deployedImage.getDeploy_name() + "成功");
            return APIResponse.success();
        }
        LOGGE.info("[AppDeployServiceImp Info]: " + "部署" + deployedImage.getDeploy_name() + "失败");
        return APIResponse.fail("部署镜像"+deployedImage.getDeploy_name()+"失败");
    }

    @Override
    public APIResponse deployTemplate(User user, DeployedTemplate deployedTemplate) {
        List<DeployContainer> deployContainerList = JSONArray.fromObject(deployedTemplate.getContainers());
        String id = deployedTemplate.getApp_id();
        Template template = templateDao.getAllTemplate()
                .stream()
                .filter(template1 -> id.equals(template1.getUuid()))
                .findFirst()
                .orElse(null);
        if(template != null) {
            //判断模板中的镜像是否存在
            List<Map<String, String>> config = JSONArray.fromObject(template.getConfig());
            for(int i = 0; i < config.size(); i++) {
                String imageName = config.get(i).get("appName");
                ImageInfo imageInfo = imageInfoDao.getImagesAll(user.getUser_uuid())
                        .stream()
                        .filter(imageInfo1 -> imageName.equals(imageInfo1.getAppName()))
                        .findFirst().orElse(null);
                if(imageInfo == null) {
                    return APIResponse.fail("部署失败，该模板中的配置信息不存在");
                }
            }
            //根据relation添加相应的环境变量
            Map<String, Object> relation = JSONObject.fromObject(template.getRelation());
            deployContainerList = buildRelations(relation, deployContainerList);
            Deployment deployment = saveDeployment(deployedTemplate, user.getUser_uuid());
            if(deployment != null) {
                //分别部署模板中的镜像
                for(int i = 0; i < deployContainerList.size(); i++) {
                    K8sClient k8sClient = new K8sClient();
                    // 创建RC
                    DeployContainer deployContainer = (DeployContainer) JSONObject.toBean(JSONObject.fromObject(deployContainerList.get(i)), DeployContainer.class);
                    ReplicationController replicationController = k8sClient.createRC(deployedTemplate, deployContainer);
                    // 创建Service
                    io.fabric8.kubernetes.api.model.Service service = k8sClient.createService(deployedTemplate, deployContainer);
                    K8s_Rc rc = saveRc(deployedTemplate, deployment, service, replicationController);
                    K8s_Service svc = saveService(deployedTemplate ,deployment,service );
                    if(rc == null || svc == null) {
                        return APIResponse.fail("部署模板" + deployedTemplate.getDeploy_name() + "失败");
                    }
                }
                //修改模板部署次数
                int count = template.getDeploycount() + 1;
                templateDao.updateDeployCount(template.getUuid(), count);
                //添加用户日志
                DateTime now = DateTime.now();
                String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
                UserLog userLog = new UserLog (
                        UuidUtil.getUUID(),
                        user.getUser_uuid(),
                        user.getUserName(),
                        SystemConst.APPLICATION,
                        deployment.getDeploy_uuid(),
                        "创建应用" + deployment.getDeploy_name(),
                        "0",
                        nowStr );
                userLogDao.addUserLog(userLog);
                return APIResponse.success();
            }
        }
        return APIResponse.fail("部署失败，被部署的模板不存在");
    }

    /**
     * 停止应用
     * @param user
     * @param deploymentId
     * @return
     */
    @Override
    public APIResponse stopApp(User user, String deploymentId) {
        try{
            List<K8s_Rc> rcs = k8sRcDao.getAllRc()
                    .stream()
                    .filter(k8s_rc -> k8s_rc.getDeployment_uuid().equals(deploymentId))
                    .collect(Collectors.toList());
            Deployment deployment = deploymentDao.getDeployments(user.getUser_uuid())
                    .stream()
                    .filter(deployment1 -> deployment1.getDeploy_uuid().equals(deploymentId))
                    .findFirst().orElse(null);
            k8sRcDao.updateReplicas(deploymentId, "0");
            K8sClient k8sClient = new K8sClient();
            for(int i = 0; i< rcs.size(); i++) {
                k8sClient.updateReplicas(rcs.get(i),"0");
            }
            //添加用户日志
            DateTime now = DateTime.now();
            String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
            UserLog userLog = new UserLog (
                    UuidUtil.getUUID(),
                    user.getUser_uuid(),
                    user.getUserName(),
                    SystemConst.APPLICATION,
                    deploymentId,
                    "停止应用" + deployment.getDeploy_name(),
                    "0",
                    nowStr );
            userLogDao.addUserLog(userLog);
            return APIResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGE.info("[AppDeployServiceImp Info]: " + "停止应用失败");
        }
        return APIResponse.fail("停止应用失败");
    }

    /**
     * 启动已停止的应用
     * @param user
     * @param deploymentId
     * @return
     */
    @Override
    public APIResponse startApp(User user, String deploymentId) {
        try {
            List<K8s_Rc> rcs = k8sRcDao.getAllRc()
                    .stream()
                    .filter(k8s_rc -> k8s_rc.getDeployment_uuid().equals(deploymentId))
                    .collect(Collectors.toList());
            Deployment deployment = deploymentDao.getDeployments(user.getUser_uuid())
                    .stream()
                    .filter(deployment1 -> deployment1.getDeploy_uuid().equals(deploymentId))
                    .findFirst().orElse(null);
            K8sClient k8sClient = new K8sClient();
            for(int i = 0; i< rcs.size(); i++) {
                K8s_Rc rc = rcs.get(i);
                k8sClient.updateReplicas(rc, rc.getDesiredCount());
                k8sRcDao.updateReplicas(deploymentId, rc.getDesiredCount());
            }
            //添加用户日志
            DateTime now = DateTime.now();
            String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
            UserLog userLog = new UserLog (
                    UuidUtil.getUUID(),
                    user.getUser_uuid(),
                    user.getUserName(),
                    SystemConst.APPLICATION,
                    deploymentId,
                    "启动应用" + deployment.getDeploy_name(),
                    "0",
                    nowStr );
            userLogDao.addUserLog(userLog);
            return APIResponse.success();
        } catch (Exception e) {
            LOGGE.info("[AppDeployServiceImp Info]: " + "启动应用失败");
        }
        return APIResponse.fail("启动应用失败");
    }

    /**
     * 服务伸缩
     * @param user
     * @param serviceName
     * @param instanceNum
     * @return
     */
    @Override
    public APIResponse scaleService(User user, String serviceName, String instanceNum) {
        try {
            K8s_Rc rc = k8sRcDao.getAllRc()
                    .stream()
                    .filter(rc1 -> rc1.getName().equals(serviceName))
                    .findFirst().orElse(null);
            K8s_Service service = k8sServiceDao.getAllService()
                    .stream()
                    .filter(k8s_service -> k8s_service.getName().equals(serviceName))
                    .findFirst().orElse(null);
            K8sClient k8sClient =new K8sClient();
            if(k8sClient.updateReplicas(rc, instanceNum)) {
                //更新数据库中对应rc的replicas
                k8sRcDao.updateReplicas(rc.getUuid(), instanceNum);
                //添加用户日志
                DateTime now = DateTime.now();
                String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
                UserLog userLog = new UserLog (
                        UuidUtil.getUUID(),
                        user.getUser_uuid(),
                        user.getUserName(),
                        SystemConst.K8S_SERVICE,
                        service.getUuid(),
                        "修改服务实例数为"+instanceNum,
                        "0",
                        nowStr );
                userLogDao.addUserLog(userLog);
                return APIResponse.success("伸缩"+serviceName + "成功");
            } else {
                return APIResponse.fail("伸缩"+serviceName + "失败");
            }
        } catch (Exception e){
            e.printStackTrace();
            LOGGE.info("[AppDeployServiceImp Info]: " + "伸缩服务"+serviceName+"失败");
        }
        return APIResponse.fail("伸缩"+serviceName + "失败");
    }

    @Override
    public APIResponse checkDeployName(User user, String deployName) {
        LOGGE.info("[AppDeployServiceImp Info]: " + "检查部署名称+"+deployName+"是否唯一");
        Deployment deployment = new Deployment();
        try {
            deployment = deploymentDao.getAllDeployments().stream()
                    .filter(deployment1 -> deployment1.getDeploy_name().equals(deployName))
                    .findFirst().orElse(null);
            if(deployment == null) {
                return APIResponse.success(true);
            } else {
                return APIResponse.success(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return APIResponse.fail("检查失败");
    }

    /**
     * 想tl_deployment表中保存数据
     * @param deployedApp
     * @param userId
     * @return
     */
    private Deployment saveDeployment(DeployedApp deployedApp, String userId){
        Deployment deployment = new Deployment();
        deployment.setDeploy_uuid(UuidUtil.getUUID());
        deployment.setDeploy_name(deployedApp.getDeploy_name());
        deployment.setDeploy_type(deployedApp.getDeploy_type());
        deployment.setDescription(deployedApp.getDescription());
        deployment.setUser_uuid(userId);
        if(deployedApp.getDeploy_type().equals("docker")) {
            deployment.setImage_uuid(deployedApp.getApp_id());
            deployment.setTemplate_uuid("");
        } else {
            deployment.setImage_uuid("");
            deployment.setTemplate_uuid(deployedApp.getApp_id());
        }
        DateTime now = DateTime.now();
        String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
        deployment.setCreate_time(nowStr);
        deployment.setUpdate_time(nowStr);
        try {
            LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_deployment表中增加数据");
            if(deploymentDao.addDeployment(deployment)>0) {
                return deployment;
            }
        }
        catch (Exception e) {
            LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_deployment表中增加数据失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向tl_rc表中保存数据
     * @param deployedApp
     * @param deployment
     * @param service
     * @param replicationController
     * @return
     */
    private K8s_Rc saveRc(DeployedApp deployedApp, Deployment deployment, io.fabric8.kubernetes.api.model.Service service, ReplicationController replicationController) {
        K8s_Rc rc = new K8s_Rc();
        K8s_Service svc = new K8s_Service();
        rc.setUuid(UuidUtil.getUUID());
        rc.setDeployment_uuid(deployment.getDeploy_uuid());
        rc.setName(service.getMetadata().getName());
        rc.setNamespace("default");
        rc.setReplicas(replicationController.getSpec().getReplicas().toString());
        rc.setDesiredCount(rc.getReplicas());
        Map<String, String> selectors = service.getSpec().getSelector();
        rc.setSelector(JSONObject.fromObject(selectors).toString());
        Map<String, String> template = new HashMap<>();
        //template.put("metadata", JSONObject.fromObject(replicationController.getMetadata()).toString());
        //template.put("spec",JSONObject.fromObject(replicationController.getSpec()).toString());
        rc.setTemplate(JSONObject.fromObject(replicationController.getSpec().getTemplate()).toString());
        rc.setCreate_time(deployment.getCreate_time());
        rc.setUpdate_time(deployment.getUpdate_time());
        try {
            LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_rc表中增加数据");
            if(k8sRcDao.addRc(rc)>0){
                return rc;
            };
        } catch (Exception e) {
            LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_rc表中增加数据失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向tl_svc表中保存数据
     * @param deployedApp
     * @param deployment
     * @param service
     * @return
     */
    private K8s_Service saveService( DeployedApp deployedApp, Deployment deployment, io.fabric8.kubernetes.api.model.Service service) {
        K8s_Service svc = new K8s_Service();
        svc.setUuid(UuidUtil.getUUID());
        svc.setDeployment_uuid(deployment.getDeploy_uuid());
        svc.setName(service.getMetadata().getName());
        svc.setNamespace("default");
        svc.setPorts(JSONArray.fromObject(service.getSpec().getPorts()).toString());
        svc.setType("NodeType");
        svc.setSelector(JSONObject.fromObject(service.getSpec().getSelector()).toString());
        svc.setCreate_time(deployment.getCreate_time());
        svc.setUpdate_time(deployment.getUpdate_time());
        K8sClient k8sClient = new K8sClient();
        List<NodeAddress> ips = k8sClient.getNode().get(0).getStatus().getAddresses();
        for(int i = 0 ; i < ips.size(); i++) {
            if(ips.get(i).getType().equals("LegacyHostIP")) {
                svc.setIp(ips.get(i).getAddress());
                break;
            }
        }
        try {
            LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_svc表中增加数据");
            if( k8sServiceDao.addService(svc) > 0 ){
                return svc;
            };
        } catch (Exception e) {
            LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_deployment表中增加数据失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建模板之间的关系
     * @param relation
     * @param deployContainerList
     * @return
     */
    private List<DeployContainer> buildRelations(Map<String, Object> relation, List<DeployContainer> deployContainerList){
        for(int i = 0 ; i < deployContainerList.size(); i++) {
            JSONObject jsonObject_from = JSONObject.fromObject(deployContainerList.get(i));
            List<Map<String, String>> env = JSONArray.fromObject(jsonObject_from.get("env"));
            String appName = (String) jsonObject_from.get("imageName");
            if(relation.get(appName)!=null) {
                Map<String, String> relation_temp = JSONObject.fromObject(relation.get(appName));
                for(int j = 0; j < relation_temp.size(); j++) {
                    relation_temp.forEach((key, value) -> {
                        String[] portNames = value.split(",");
                        JSONObject jsonObject_to = null;
                        for (int k = 0; k< deployContainerList.size(); k++) {
                            if(JSONObject.fromObject(deployContainerList.get(k)).get("imageName").equals(key)) {
                                jsonObject_to = JSONObject.fromObject(deployContainerList.get(k));
                                break;
                            }
                        }
                        List<Map<String, Object>> ports = JSONArray.fromObject(jsonObject_to.get("ports"));
                        for(int p = 0; p<portNames.length; p++) {
                            Map<String, String> envTemp = new HashMap<>();
                            for(int q = 0; q <ports.size(); q++) {
                                Map<String, Object> portDetail = ports.get(q);
                                if(portDetail.get("name").equals(portNames[p])){
                                    envTemp.put("name", portNames[p]);
                                    envTemp.put("value", portDetail.get("port").toString());
                                }
                            }
                            env.add(envTemp);
                        }
                    });
                }
                jsonObject_from.replace("env", env);
                DeployContainer deployContainer = (DeployContainer) JSONObject.toBean(jsonObject_from, DeployContainer.class);
                deployContainerList.set(i, deployContainer);
            }
        }
        return deployContainerList;
    }

}

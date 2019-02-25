package com.tanli.cloud.service;

import com.tanli.cloud.dao.DeploymentDao;
import com.tanli.cloud.dao.K8sPodDao;
import com.tanli.cloud.dao.K8sRcDao;
import com.tanli.cloud.dao.K8sServiceDao;
import com.tanli.cloud.model.*;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.K8sClient;
import com.tanli.cloud.utils.UuidUtil;
import io.fabric8.kubernetes.api.model.*;
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

    private static final Logger LOGGE = LoggerFactory.getLogger(AppDeployServiceImp.class);

    @Override
    public APIResponse deployImage(User user, DeployedImage deployedImage) {
        JSONObject jsonObject=JSONObject.fromObject(deployedImage.getContainer());
        DeployContainer deployContainer = (DeployContainer) JSONObject.toBean(jsonObject, DeployContainer.class);
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
            if( rc != null && svc !=null) {
                // 更新pod到数据库中
                List<Pod> pods = k8sClient.getPod(service.getSpec().getSelector());
                pods.stream()
                        .forEach(pod -> {
                            savePod(pod, rc, svc);
                        });
                LOGGE.info("[AppDeployServiceImp Info]: " + "部署" + deployedImage.getDeploy_name() + "成功");
                return APIResponse.success();
            }
        }
        LOGGE.info("[AppDeployServiceImp Info]: " + "部署" + deployedImage.getDeploy_name() + "失败");
        return APIResponse.fail("部署镜像"+deployedImage.getDeploy_name()+"失败");
    }

    @Override
    public APIResponse deployTemplate(User user, DeployedTemplate deployedTemplate) {
        List<DeployContainer> deployContainerList = JSONArray.fromObject(deployedTemplate.getContainers());
        for(int i = 0; i < deployContainerList.size(); i++) {
            Deployment deployment = saveDeployment(deployedTemplate, user.getUser_uuid());
            if(deployment != null) {
                K8sClient k8sClient = new K8sClient();
                // 创建RC
                ReplicationController replicationController = k8sClient.createRC(deployedTemplate, deployContainerList.get(i));
                // 创建Service
                io.fabric8.kubernetes.api.model.Service service = k8sClient.createService(deployedTemplate, deployContainerList.get(i));
                K8s_Rc rc = saveRc(deployedTemplate, deployment, service, replicationController);
                K8s_Service svc = saveService(deployedTemplate ,deployment,service );
                if(rc == null || svc == null) {
                    return APIResponse.fail("部署模板" + deployedTemplate.getDeploy_name() + "失败");
                }
            }
        }
        return APIResponse.success();
    }

    /**
     * 想tl_pod表中保存数据
     * @param pod
     * @param rc
     * @param svc
     * @return
     */
    private int savePod(Pod pod, K8s_Rc rc, K8s_Service svc) {
        K8s_Pod temp = new K8s_Pod();
        temp.setUuid(UuidUtil.getUUID());
        temp.setRc_uuid(rc.getUuid());
        temp.setSvc_uuid(svc.getUuid());
        temp.setName(pod.getMetadata().getName());
        temp.setNamespace(pod.getMetadata().getNamespace());
        //pod是单容器的
        temp.setImage(pod.getSpec().getContainers().get(0).getImage());
        temp.setRestartCount(pod.getStatus().getContainerStatuses().get(0).getRestartCount());
        temp.setPodIP(pod.getStatus().getPodIP());
        temp.setHostIP(pod.getStatus().getHostIP());
        temp.setStatus(pod.getStatus().getPhase());
        DateTime now = DateTime.now();
        String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
        temp.setUpdate_time(nowStr);
        try {
            return k8sPodDao.addPod(temp);
        } catch (Exception e) {
            LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_pod表中增加数据失败");
            e.printStackTrace();
        }
        return 0;
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
        rc.setName(deployedApp.getDeploy_name() + "-rc");
        rc.setNamespace("default");
        rc.setReplicas(rc.getReplicas());
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
        svc.setName(deployedApp.getDeploy_name() + "-svc");
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
}

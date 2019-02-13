package com.tanli.cloud.service;

import com.tanli.cloud.dao.DeploymentDao;
import com.tanli.cloud.dao.K8sRcDao;
import com.tanli.cloud.dao.K8sServiceDao;
import com.tanli.cloud.model.*;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.K8sClient;
import com.tanli.cloud.utils.UuidUtil;
import io.fabric8.kubernetes.api.model.ReplicationController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AppDeployServiceImp implements AppDeployService {

    @Autowired
    private DeploymentDao deploymentDao;
    @Autowired
    private K8sRcDao k8sRcDao;
    @Autowired
    private K8sServiceDao k8sServiceDao;

    private static final Logger LOGGE = LoggerFactory.getLogger(AppDeployServiceImp.class);

    @Override
    public APIResponse deployImage(User user, DeployedImage deployedImage) {
        JSONObject jsonObject=JSONObject.fromObject(deployedImage.getContainer());
        DeployContainer deployContainer = (DeployContainer) JSONObject.toBean(jsonObject, DeployContainer.class);

        Deployment deployment = new Deployment();
        deployment.setDeploy_uuid(UuidUtil.getUUID());
        deployment.setDeploy_name(deployedImage.getDeploy_name());
        deployment.setDeploy_type(deployedImage.getDeploy_type());
        deployment.setDescription(deployedImage.getDescription());
        deployment.setUser_uuid(user.getUser_uuid());
        deployment.setImage_uuid(deployedImage.getApp_id());
        deployment.setTemplate_uuid("");
        DateTime now = DateTime.now();
        String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
        deployment.setCreate_time(nowStr);
        deployment.setUser_uuid(nowStr);
        try {
            LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_deployment表中增加数据");
            // 向tl_deployment表中增加数据
            if(deploymentDao.addDeployment(deployment) == 1) {
                K8sClient k8sClient = new K8sClient();
                // 创建RC
                ReplicationController replicationController = k8sClient.createRC(deployedImage);
                // 创建Service
                io.fabric8.kubernetes.api.model.Service service = k8sClient.createService(deployedImage);
                // 将RC和Service数据保存进数据库中
                K8s_Rc rc = new K8s_Rc();
                K8s_Service svc = new K8s_Service();
                rc.setUuid(UuidUtil.getUUID());
                rc.setDeployment_uuid(deployment.getDeploy_uuid());
                rc.setName(deployedImage.getDeploy_name() + "-rc");
                rc.setNamespace("default");
                rc.setReplicas(String.valueOf(deployContainer.getReplicas()));
                Map<String, String> selectors = service.getSpec().getSelector();
                rc.setSelector(JSONObject.fromObject(selectors).toString());
                Map<String, String> template = new HashMap<>();
//                template.put("metadata", JSONObject.fromObject(replicationController.getMetadata()).toString());
//                template.put("spec",JSONObject.fromObject(replicationController.getSpec()).toString());
                rc.setTemplate(JSONObject.fromObject(replicationController.getSpec().getTemplate()).toString());
                rc.setCreate_time(nowStr);
                rc.setUpdate_time(nowStr);
                svc.setUuid(UuidUtil.getUUID());
                svc.setDeployment_uuid(deployment.getDeploy_uuid());
                svc.setName(deployedImage.getDeploy_name() + "-svc");
                svc.setNamespace("default");
                svc.setPorts(JSONArray.fromObject(deployContainer.getPorts()).toString());
                svc.setType("NodeType");
                svc.setSelector(JSONObject.fromObject(selectors).toString());
                svc.setCreate_time(nowStr);
                svc.setUpdate_time(nowStr);
                LOGGE.info("[AppDeployServiceImp Error]: " + "向tl_rc和tl_svc表中增加数据");
                if (k8sRcDao.addRc(rc)>0 && k8sServiceDao.addService(svc) > 0) {

                } else {
                    LOGGE.info("[AppDeployServiceImp Error]: " + "向tl_rc和tl_svc表中增加数据");
                    return APIResponse.fail("向tl_rc和tl_svc表中增加数据");
                }
            } else {
                LOGGE.info("[AppDeployServiceImp Error]: " + "向tl_deployment表中增加数据失败");
                return APIResponse.fail("向tl_deployment表中增加数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.fail("向tl_deployment表中增加数据失败");
        }
        return null;
    }

}

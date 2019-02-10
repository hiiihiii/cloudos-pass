package com.tanli.cloud.service;

import com.tanli.cloud.dao.DeploymentDao;
import com.tanli.cloud.model.DeployContainer;
import com.tanli.cloud.model.DeployedImage;
import com.tanli.cloud.model.Deployment;
import com.tanli.cloud.model.Kubernetes.*;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.UuidUtil;
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

    private static final Logger LOGGE = LoggerFactory.getLogger(AppDeployServiceImp.class);

    @Override
    public APIResponse deployImage(User user, DeployedImage deployedImage) {
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
                // 创建RC

                // 创建Service
            } else {
                LOGGE.info("[AppDeployServiceImp Info]: " + "向tl_deployment表中增加数据失败");
                return APIResponse.fail("向tl_deployment表中增加数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.fail("向tl_deployment表中增加数据失败");
        }

        return null;
    }

    private ReplicationController buildRc(DeployedImage deployedImage){
        JSONObject jsonObject=JSONObject.fromObject(deployedImage.getContainer());
        DeployContainer deployContainer = (DeployContainer) JSONObject.toBean(jsonObject, DeployContainer.class);


        Container container =  buildContainer(deployContainer);
        Map<String, String> selector = new HashMap<String, String>();
        selector.put("app", deployContainer.getImageName());
        Container[] containers = new Container[1];
        containers[0] = container;
        PodSpec podSpec = new PodSpec(containers, "Always");
        ObjectMeta podMeta = new ObjectMeta(deployContainer.getImageName(), "default", selector);
        PodTemplateSpec template = new PodTemplateSpec(podMeta, podSpec);
        ObjectMeta rcMeta = new ObjectMeta(deployContainer.getImageName() + "-rc","default", null);
        ReplicationControllerSpec rcSpec = new ReplicationControllerSpec(deployContainer.getReplicas(), selector, template);
        return new ReplicationController("v1", "ReplicationController", rcMeta, rcSpec);

    }

    private Container buildContainer(DeployContainer deployContainer) {
        String[] args = deployContainer.getArgs();
        String[] cmd = deployContainer.getCommand();
        String image = deployContainer.getImage_source_url();
        String imagePullPolicy = "IfNotPresent";
        String imageName = deployContainer.getImageName();
        String workingDir = deployContainer.getWorkingDir();

        int envLength = deployContainer.getEnv().length;
        EnvVar[] envVars = new EnvVar[envLength];
        Map<String, String>[] envMap = deployContainer.getEnv();
        for(int i = 0; i < envLength; i++) {
            EnvVar temp = new EnvVar();
            temp.setName(envMap[i].get("name"));
            temp.setValue(envMap[i].get("value"));
            envVars[i] = temp;
        }

        int portsLength = deployContainer.getPorts().length;
        ContainerPort[] ports = new ContainerPort[portsLength];
        Map<String, Object>[] portsMap = deployContainer.getPorts();
        for(int i = 0; i < portsLength; i++) {
            ContainerPort tem = new ContainerPort();
            tem.setContainerPort(Integer.parseInt(portsMap[i].get("port").toString()));
            ports[i] = tem;
        }

        ResourcesRequirements requirements = new ResourcesRequirements();
        Map<String, String> limits = deployContainer.getLimits();
        Map<String, String> requests = deployContainer.getRequests();
        requirements.setLimits(limits);
        requirements.setRequests(requests);

        return new Container(args, cmd, envVars, image, imagePullPolicy,
                imageName, ports, requirements, workingDir);
    }

    //构建SVC
    private KService buildSVC(DeployedImage deployedImage) {
        return new KService();
    }

}

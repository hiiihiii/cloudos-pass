package com.tanli.cloud.service;

import com.tanli.cloud.dao.DeploymentDao;
import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.dao.K8sServiceDao;
import com.tanli.cloud.dao.TemplateDao;
import com.tanli.cloud.model.Deployment;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.K8s_Service;
import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.response.R_Deployment;
import com.tanli.cloud.model.response.R_Service;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.K8sClient;
import io.fabric8.kubernetes.api.model.Pod;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@Service
public class ApplicationServiceImp implements ApplicationService{

    @Autowired
    private DeploymentDao deploymentDao;
    @Autowired
    private ImageInfoDao imageInfoDao;
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private K8sServiceDao k8sServiceDao;

    private static final org.slf4j.Logger LOGGE = LoggerFactory.getLogger(ApplicationServiceImp.class);

    @Override
    public APIResponse getApplications(User user) {
        String userid = user.getUser_uuid();
        List<R_Deployment> r_deployments = new ArrayList<>();
        try {
            List<Deployment> deployments = deploymentDao.getDeployments(userid);
            deployments.forEach(deployment -> {
                R_Deployment r_deployment = new R_Deployment();
                r_deployment.setDeploy_name(deployment.getDeploy_name());
                r_deployment.setDeploy_uuid(deployment.getDeploy_uuid());
                r_deployment.setUpdate_time(deployment.getUpdate_time());
                r_deployment.setUser_uuid(userid);
                r_deployment.setDescription(deployment.getDescription());
                //设置部署来源和来源名称
                if(deployment.getDeploy_type().equals("docker")){
                    r_deployment.setSource_type("镜像");
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo = imageInfoDao.getImagesAll(userid)
                            .stream()
                            .filter(imageInfo1 -> imageInfo1.getApp_id().equals(deployment.getImage_uuid()))
                            .findFirst()
                            .orElse(null);
                    r_deployment.setSource_name(imageInfo.getAppName());
                } else {
                    r_deployment.setSource_type("模板");
                    Template template = new Template();
                    template = templateDao.getAllTemplate()
                            .stream()
                            .filter(template1 -> template1.getUuid().equals(deployment.getTemplate_uuid()))
                            .findFirst()
                            .orElse(null);
                    r_deployment.setSource_name(template.getTemplateName());
                }
                //根据相关的所有pod设置status
                r_deployment.setStatus(setStatus(deployment));
                r_deployments.add(r_deployment);
            });
            return APIResponse.success(r_deployments);
        } catch (Exception e) {
            LOGGE.info("[ApplicationServiceImp Info]: " + "获取应用信息失败");
            e.printStackTrace();
        }
        return APIResponse.fail("获取应用信息失败");
    }

    @Override
    public APIResponse getServiceInfo(User user, String deployid) {
        List<R_Service> r_services = new ArrayList<>();
        List<K8s_Service> services = k8sServiceDao.getAllService()
                .stream()
                .filter(k8s_service -> k8s_service.getDeployment_uuid().equals(deployid))
                .collect(Collectors.toList());
        services.stream().forEach(k8s_service -> {
            R_Service service = new R_Service();
            service.setName(k8s_service.getName());
            List<String> ips = new ArrayList<>();
            List<Map<String, Object>> port = JSONArray.fromObject(k8s_service.getPorts());
            for(int i = 0; i < port.size(); i++) {
                String temp = port.get(i).get("nodePort").toString();
                ips.add(k8s_service.getIp() + ":" + temp);
            }
            service.setIps(ips);
            // 根据相关Pod设置Status
            K8sClient k8sClient = new K8sClient();
            Map<String, String> selectors = JSONObject.fromObject(k8s_service.getSelector());
            List<Pod> pods = k8sClient.getPod(selectors);
            service.setStatus("Running");
            r_services.add(service);
        });
        return APIResponse.success(r_services);
    }

    //设置deployment的状态
    private String setStatus(Deployment deployment){
        Map<String, String> selectors = new HashMap<>();
        K8s_Service svc = k8sServiceDao.getAllService()
                .stream()
                .filter(k8s_service -> k8s_service.getDeployment_uuid().equals(deployment.getDeploy_uuid()))
                .findFirst()
                .orElse(null);
        selectors = JSONObject.fromObject(svc.getSelector());
        K8sClient k8sClient = new K8sClient();
        List<Pod> pods = k8sClient.getPod(selectors);
        String status = "";
        for(int i = 0; i < pods.size(); i++) {
            String temp = pods.get(i).getStatus().getPhase();
            if(temp.equals("")) {

            } else if(temp.equals("")) {

            } else {

            }
        }
        return "";
    }
}

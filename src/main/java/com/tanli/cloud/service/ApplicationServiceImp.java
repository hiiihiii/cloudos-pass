package com.tanli.cloud.service;

import com.tanli.cloud.constant.SystemConst;
import com.tanli.cloud.dao.*;
import com.tanli.cloud.model.*;
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
    @Autowired
    private K8sRcDao k8sRcDao;

    private static final org.slf4j.Logger LOGGE = LoggerFactory.getLogger(ApplicationServiceImp.class);

    /**
     * 获取部署记录
     * @param user
     * @return
     */
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
                //根据相关的所有Service设置status
                r_deployment.setStatus(getAppStatus(deployment));
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
            service.setStatus(getSvcStatus(k8s_service));
            r_services.add(service);
        });
        return APIResponse.success(r_services);
    }

    /**
     * 设置应用状态
     * @param deployment
     * @return
     */
    private String getAppStatus(Deployment deployment){
        String statusResult = "";
        List<K8s_Service> svc = k8sServiceDao.getAllService()
                .stream()
                .filter(k8s_service -> k8s_service.getDeployment_uuid().equals(deployment.getDeploy_uuid()))
                .collect(Collectors.toList());
        int pendingCount = 0;
        int stopCount = 0;
        for(int i = 0; i < svc.size(); i++) {
            K8s_Service k8s_service = svc.get(i);
            String svcStatus = getSvcStatus(k8s_service);
            switch (svcStatus) {
                case SystemConst.SVC_RUNNING_STATUS: {
                    statusResult = SystemConst.SVC_RUNNING_STATUS;
                    return statusResult;
                }
                case SystemConst.SVC_STOP_STATUS: {
                    stopCount += 1;
                    break;
                }
                case SystemConst.SVC_CREATING_STATUS: {
                    pendingCount += 1;
                    break;
                }
            }
        }
        if(pendingCount == svc.size()) {
            statusResult = SystemConst.SVC_CREATING_STATUS;
        } else if(stopCount == svc.size()) {
            statusResult = SystemConst.SVC_STOP_STATUS;
        } else {
            statusResult = SystemConst.SVC_UNKNOWN_STATUS;
        }
        return statusResult;
    }

    /**
     * 设置Svc的状态
     * @param svc
     * @return
     */
    private String getSvcStatus(K8s_Service svc) {
        String result = "";
        Map<String, String>selectors = JSONObject.fromObject(svc.getSelector());
        K8sClient k8sClient = new K8sClient();
        List<Pod> pods = k8sClient.getPod(selectors);
        int pendingCount = 0;
        if(0 == pods.size()) {
            result = SystemConst.SVC_STOP_STATUS;
        } else {
            for(int i = 0; i < pods.size(); i++) {
                String temp = pods.get(i).getStatus().getPhase();
                if(temp.equals("Running")) {
                    result = SystemConst.SVC_RUNNING_STATUS;
                    return result;
                } else if(temp.equals("Pending")) {
                    pendingCount += 1;
                }
            }
            if(pendingCount == pods.size()) {
                result = SystemConst.SVC_CREATING_STATUS;
            } else {
                result = SystemConst.SVC_UNKNOWN_STATUS;
            }
        }
        return result;
    }
}

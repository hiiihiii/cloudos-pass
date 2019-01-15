package com.tanli.cloud.service;

import com.tanli.cloud.dao.DeploymentDao;
import com.tanli.cloud.model.DeployedImage;
import com.tanli.cloud.model.Deployment;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.UuidUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
            if(deploymentDao.addDeployment(deployment) == 1) {

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
}

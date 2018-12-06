package com.tanli.cloud.service;

import com.tanli.cloud.dao.TemplateDao;
import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.utils.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanli on 2018/12/6 0006.
 */
@Service
public class TemplateServiceImp implements TemplateService{

    private static final Logger LOGGE = LoggerFactory.getLogger(AppStoreServiceImp.class);
    @Autowired
    private TemplateDao templateDao;

    @Override
    public APIResponse getTemplate(LoginingUser user) {
        List<Template> templateList = new ArrayList<Template>();
        try{
            templateList = templateDao.getAllTemplate(user.getUser_uuid());
            return APIResponse.success(templateList);
        } catch (Exception e){
            LOGGE.info("获取镜像模板失败");
            e.printStackTrace();
        }
        return APIResponse.fail("获取镜像模板失败");
    }
}

package com.tanli.cloud.service;

import com.tanli.cloud.dao.TemplateDao;
import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tanli on 2018/12/6 0006.
 */
@Service
public class TemplateServiceImp implements TemplateService{

    private static final Logger LOGGE = LoggerFactory.getLogger(AppStoreServiceImp.class);
    @Autowired
    private TemplateDao templateDao;

    @Override
    public APIResponse getTemplate(User user) {
        List<Template> templateList = new ArrayList<Template>();
        try{
            if(("public_user").equals(user.getRole_name())){
                templateList = templateDao.getAllTemplate()
                        .stream()
                        .filter(template -> ("1").equals(template.getIsPublish()))
                        .collect(Collectors.toList());
            } else if(("admin_user").equals(user.getRole_name())) {
                templateList = templateDao.getAllTemplate()
                        .stream()
                        .filter(template -> user.getUser_uuid().equals(template.getUserId()))
                        .collect(Collectors.toList());
            }
            return APIResponse.success(templateList);
        } catch (Exception e){
            LOGGE.info("[TemplateServiceImp Info]: " + "获取镜像模板失败");
            e.printStackTrace();
        }
        return APIResponse.fail("获取镜像模板失败");
    }

    @Override
    public APIResponse getTemplateDetail(User user, String templateId) {
        Template template = new Template();
        try {
            template = templateDao.getAllTemplate()
                    .stream().
                    filter(template1 -> template1.getUuid().equals(templateId))
                    .findFirst()
                    .orElse(null);
            if(null == template) {
                return APIResponse.fail(templateId + "不存在");
            } else {
                return APIResponse.success(template);
            }
        } catch (Exception e) {
            LOGGE.info("[TemplateServiceImp Info]: " + "获取" + templateId + "镜像模板失败");
            return APIResponse.fail(templateId + "获取" + templateId + "镜像模板失败");
        }
    }
}

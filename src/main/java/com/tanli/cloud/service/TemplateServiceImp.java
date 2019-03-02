package com.tanli.cloud.service;

import com.tanli.cloud.dao.TemplateDao;
import com.tanli.cloud.dao.UserLogDao;
import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.UserLog;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.UuidUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tanli on 2018/12/6 0006.
 */
@Service
public class TemplateServiceImp implements TemplateService{

    private static final Logger LOGGE = LoggerFactory.getLogger(AppStoreServiceImp.class);
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private UserLogDao userLogDao;

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

    @Override
    public APIResponse publishTemplate(User user, String templateId) {
        try {
            //操作日志
            DateTime now = DateTime.now();
            String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
            UserLog userLog = new UserLog();
            userLog.setUuid(UuidUtil.getUUID());
            userLog.setUser_id(user.getUser_uuid());
            userLog.setUsername(user.getUserName());
            userLog.setResoureType("Template");
            userLog.setResourceId(templateId);
            userLog.setOperation("发布应用模板");
            userLog.setIsDeleted("0");
            userLog.setCreate_time(nowStr);
            userLogDao.addUserLog(userLog);
            int count = templateDao.publishTemplate(templateId);
            if(count > 0) {
                return APIResponse.success();
            } else {
                LOGGE.info("[TemplateServiceImp Info]: " + "发布镜像模板" + templateId + "失败");
            }
        } catch (Exception e) {
            LOGGE.info("[TemplateServiceImp Info]: " + "发布镜像模板" + templateId + "失败");
            e.printStackTrace();
        }
        return APIResponse.fail("发布镜像模板" + templateId + "失败");
    }

    @Override
    public APIResponse deleteByIds(User user, String[] ids) {
        Map<String, Integer> result = new HashMap<>();
        int success = 0, fail = 0;
        for(int i = 0; i < ids.length; i++) {
            try {
                //操作日志
                DateTime now = DateTime.now();
                String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
                UserLog userLog = new UserLog();
                userLog.setUuid(UuidUtil.getUUID());
                userLog.setUser_id(user.getUser_uuid());
                userLog.setUsername(user.getUserName());
                userLog.setResoureType("Template");
                userLog.setResourceId(ids[0]);
                userLog.setOperation("删除应用模板");
                userLog.setIsDeleted("0");
                userLog.setCreate_time(nowStr);
                userLogDao.addUserLog(userLog);
                int count = templateDao.deleteById(ids[i]);
                if(count > 0) {
                    success += 1;
                } else {
                    fail += 1;
                    LOGGE.info("[TemplateServiceImp Info]: " + "删除镜像模板" + ids[i] + "失败");
                }
            } catch (Exception e) {
                fail += 1;
                LOGGE.info("[TemplateServiceImp Info]: " + "删除镜像模板" + ids[i] + "失败");
                e.printStackTrace();
            }
        }
        result.put("success", success);
        result.put("fail", fail);
        if(fail == ids.length){
            return APIResponse.fail("删除镜像模板失败");
        } else {
            return APIResponse.success(result);
        }
    }
}

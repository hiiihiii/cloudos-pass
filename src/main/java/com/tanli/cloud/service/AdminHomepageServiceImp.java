package com.tanli.cloud.service;

import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.dao.TemplateDao;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2019/3/7 0007.
 */
@Service
public class AdminHomepageServiceImp implements AdminHomepageService{

    @Autowired
    private ImageInfoDao imageInfoDao;
    @Autowired
    private TemplateDao templateDao;

    @Override
    public APIResponse getAllImages(User user) {
        try {
            List<ImageInfo> imageInfos = imageInfoDao.getImagesAll(user.getUser_uuid());
            return APIResponse.success(imageInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return APIResponse.fail("获取镜像数据失败");
    }

    @Override
    public APIResponse getAllTemplates(User user) {
        try {
            List<Template> templateList = templateDao.getAllTemplate()
                    .stream()
                    .filter(template -> template.getUserId().equals(user.getUser_uuid()))
                    .collect(Collectors.toList());
            return APIResponse.success(templateList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return APIResponse.fail("获取模板信息失败");
    }
}

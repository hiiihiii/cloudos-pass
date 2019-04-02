package com.tanli.cloud.service;

import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanli on 2019/3/7 0007.
 */
@Service
public class AdminHomepageServiceImp implements AdminHomepageService{

    @Autowired
    private ImageInfoDao imageInfoDao;

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

}

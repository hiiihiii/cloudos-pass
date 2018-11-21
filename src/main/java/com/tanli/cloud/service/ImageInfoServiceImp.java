package com.tanli.cloud.service;

import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.model.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tanli on 2018/11/21 0021.
 */
@Service
public class ImageInfoServiceImp implements ImageInfoService {
    @Autowired
    private ImageInfoDao imageInfoDao;

    @Override
    public int addImageInfo(ImageInfo imageInfo) {
        return imageInfoDao.addImageInfo(imageInfo);
    }
}

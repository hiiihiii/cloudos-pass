package com.tanli.cloud.service;

import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.utils.APIResponse;

import java.util.List;

/**
 * Created by tanli on 2018/11/21 0021.
 */
public interface ImageInfoService {
    public int addImageInfo(ImageInfo imageInfo);

    public APIResponse getImages(LoginingUser user, String repoType);

    public List<ImageInfo> getImagesAll(LoginingUser user);

    public int updateImageInfo(ImageInfo imageInfo);
}

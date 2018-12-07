package com.tanli.cloud.service;

import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.utils.APIResponse;

import java.util.List;

/**
 * Created by tanli on 2018/11/21 0021.
 */
public interface ImageInfoService {
    public int addImageInfo(ImageInfo imageInfo);

    public APIResponse getImages(User user, String repoType);

    public List<ImageInfo> getImagesAll(User user);

    public int updateImageInfo(ImageInfo imageInfo);
}

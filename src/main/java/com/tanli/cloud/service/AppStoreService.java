package com.tanli.cloud.service;

import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AppStoreService {

    public String uploadFile(String fileName, MultipartFile file, String type, String newFileName);

    public APIResponse uploadImage(ImageInfo imageInfo, MultipartFile logoFile, MultipartFile sourceFile, LoginingUser user);

}

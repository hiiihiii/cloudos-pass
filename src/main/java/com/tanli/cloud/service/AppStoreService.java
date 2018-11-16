package com.tanli.cloud.service;

import com.tanli.cloud.model.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface AppStoreService {

    public String uploadFile(String fileName, MultipartFile file, String type, String newFileName);

    public void uploadImage(ImageInfo imageInfo, MultipartFile logoFile, MultipartFile sourceFile);

}

package com.tanli.cloud.service;

import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.utils.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AppStoreServiceImp implements AppStoreService {

    @Autowired
    private FtpUtil ftpUtil;

    private static final Logger LOGGE = LoggerFactory.getLogger(AppStoreServiceImp.class);

    @Override
    public String uploadFile(String fileName, MultipartFile file, String type, String newFileName) {
        String path = "";
        String result = "";
        if("logo".equals(type)){
            path = "/logo";
        } else if("source".equals(type)){
            path = "/dockerfile";
        }
        try {
            InputStream inputStream = file.getInputStream();
            result = ftpUtil.uploadFile(fileName, inputStream, path, newFileName);
        } catch (IOException e){
            LOGGE.info("[AppStoreServiceImp Error]: " + fileName + "转换失败");
        }
        return result;
    }

    @Override
    public void uploadImage(ImageInfo imageInfo, MultipartFile logoFile, MultipartFile sourceFile) {
        //上传logo文件
        String originalFileName = logoFile.getOriginalFilename();
        String[] temp = originalFileName.split("\\.");
        String newFileName = imageInfo.getAppName() + "-" + imageInfo.getVersion() + "." + temp[temp.length-1];

        //必须使用originFileName
        String logoUrl = uploadFile(originalFileName, logoFile, "logo", newFileName);
        //上传源文件
//        String sourceUrl = uploadFile(fileName, sourceFile, "source");
    }




}

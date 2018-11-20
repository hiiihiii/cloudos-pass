package com.tanli.cloud.service;

import com.tanli.cloud.constant.EnvConst;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.utils.FtpUtil;
import com.tanli.cloud.utils.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Service
public class AppStoreServiceImp implements AppStoreService {

    @Autowired
    private FtpUtil ftpUtil;

    @Autowired
    private RestClient restClient;

    @Resource
    RestTemplate restTemplate;


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
        String originalFileName = logoFile.getOriginalFilename();//必须使用originFileName
        String[] temp = originalFileName.split("\\.");
        String newFileName = imageInfo.getAppName() + "-" + imageInfo.getVersion() + "." + temp[temp.length-1];
        String logoUrl = uploadFile(originalFileName, logoFile, "logo", newFileName);
        if(!("".equals(logoUrl))){
            imageInfo.setLogo_url(logoUrl);
            //上传源文件
            //originalFileName = sourceFile.getOriginalFilename();
            //temp = originalFileName.split("\\.");
            //newFileName = imageInfo.getAppName() + "-" + imageInfo.getVersion() + "." + temp[temp.length-1];
            //String sourceUrl = uploadFile(originalFileName, sourceFile, "source", newFileName);
            //if(!("".equals(sourceUrl))){
            //    imageInfo.setSource_url(sourceUrl);
            //load镜像
            String loadURL = EnvConst.docker_images_prefix + "load";
            String image = loadImage(loadURL, sourceFile);
            if(!("".equals(image))){
                //tag镜像
                String tagURL = EnvConst.docker_images_prefix + image + "/tag";
                Boolean tagImage = tagImage(tagURL, imageInfo);
                if(tagImage){
                    //push镜像
                    String pushUrl = EnvConst.docker_images_prefix + EnvConst.docker_api_ip + "/demo/" + imageInfo.getAppName() + "/push" ;
                    pushImage(pushUrl, imageInfo.getVersion());
                }
            }
        } else {
            LOGGE.info("[AppStoreServiceImp Error]: " + "上传镜像源文件失败，文件名为：" + originalFileName);
            return;
        }

        //} else {
        //    LOGGE.info("[AppStoreServiceImp Error]: " + "上传logo文件失败，文件名为：" + originalFileName);
        //    return;
        //}

    }

    public String loadImage(String url, MultipartFile sourceFile){
        String image = "";
        try {
            ResponseEntity<String> loadResponse = restTemplate.postForEntity(url, sourceFile.getBytes(), String.class);
            String msg = loadResponse.getBody().toString();
            int beginIndex = msg.indexOf("Loaded image");
            if(beginIndex > -1){
                beginIndex += 14;
                image = msg.substring(beginIndex, msg.length() - 6);
                LOGGE.info("[AppStoreServiceImp Info]: " + image + " Loaded");
            } else {
                return image;
            }
        } catch (IOException io){

        }
        return image;
    }

    public Boolean tagImage(String url, ImageInfo imageInfo){
        Boolean result = false;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("repo",EnvConst.docker_api_ip + "demo" + imageInfo.getAppName());//根据用户的repo来代替demo
        map.add("tag", imageInfo.getVersion());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        String code = response.getStatusCode().toString();
        if("201".equals(code)){
            return true;
        }
        return result;
    }

    public void pushImage(String url, String tag){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        try{
            String authStr = "{ \"username\": \"admin\", \"password\": \"Harbor12345\", \"serveraddress\": \"132.232.140.33\" }";
            String authStr_base64 = Base64.getEncoder().encodeToString(authStr.getBytes("utf-8"));
            headers.set("X-Registry-Auth", authStr_base64);
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("tag", tag);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class);
            response.getStatusCode();
        } catch (UnsupportedEncodingException e){

        } catch (HttpClientErrorException e){

        }
    }

}

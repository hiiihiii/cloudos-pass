package com.tanli.cloud.service;

import com.tanli.cloud.constant.EnvConst;
import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.model.response.Repository;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.FtpUtil;
import com.tanli.cloud.utils.UuidUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
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
import java.util.*;

@Service
public class AppStoreServiceImp implements AppStoreService {

    @Autowired
    private FtpUtil ftpUtil;
    @Resource
    RestTemplate restTemplate;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ImageInfoDao imageInfoDao;

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
    public APIResponse uploadImage(ImageInfo imageInfo, MultipartFile logoFile, MultipartFile sourceFile, LoginingUser user) {
        //上传logo文件
        String originalFileName = logoFile.getOriginalFilename();//必须使用originFileName
        String[] temp = originalFileName.split("\\.");
        String newFileName = imageInfo.getAppName() + "-" + imageInfo.getVersion() + "." + temp[temp.length-1];
        String logoUrl = uploadFile(originalFileName, logoFile, "logo", newFileName);
        if(!("".equals(logoUrl))){
            //List<Repository> repositories = repositoryService.getRepoByUserid(imageInfo.getUser_id());
            Repository repository = repositoryService.getRepoByUserid(user.getUser_uuid()).stream()
                    .filter(repo -> repo.getRepo_type().equals(imageInfo.getType()))
                    .findFirst()
                    .get();
            LOGGE.info("[AppStoreServiceImp Info]: " + repository);
            //load镜像
            String loadURL = EnvConst.docker_images_prefix + "load";
            String image = loadImage(loadURL, sourceFile);
            if(!("".equals(image))){
                //tag镜像
                String tagURL = EnvConst.docker_images_prefix + image + "/tag";
                Boolean isTaged = tagImage(tagURL, imageInfo, repository.getRepo_name());
                if(isTaged){
                    //push镜像
                    String pushUrl = EnvConst.docker_images_prefix + EnvConst.docker_api_ip + "/" + repository.getRepo_name() +"/" + imageInfo.getAppName() + "/push" ;
                    Boolean isPushed = pushImage(pushUrl, imageInfo.getVersion());
                    if(isPushed){
                        ImageInfo exist = imageInfoDao.getImages(repository.getRepo_uuid(), user.getUser_uuid())
                                .stream()
                                .filter(imageInfo1 -> imageInfo1.getAppName().equals(imageInfo.getAppName()))
                                .findFirst()
                                .orElse(null);
                        imageInfo.setRepo_id(repository.getRepo_uuid());
                        imageInfo.setUser_id(user.getUser_uuid());
                        imageInfo.setLogo_url(logoUrl);
                        DateTime now = DateTime.now();
                        String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
                        //数据库中是否已存在同名镜像
                        if(exist == null) {
                            imageInfo.setApp_id(UuidUtil.getUUID());

                            Map<String, Object> tem_map = new HashMap<>();
                            tem_map.put(imageInfo.getVersion(), imageInfo.getV_description());
                            imageInfo.setV_description(JSONObject.fromObject(tem_map).toString());

                            tem_map.clear();
                            tem_map.put(imageInfo.getVersion(), EnvConst.docker_api_ip + "/"+repository.getRepo_name() + "/" + repository.getRepo_name() + ":" + imageInfo.getVersion());
                            imageInfo.setSource_url(JSONObject.fromObject(tem_map).toString());

                            tem_map.clear();
                            tem_map.put(imageInfo.getVersion(), JSONObject.fromObject(imageInfo.getMetadata()));
                            imageInfo.setMetadata(JSONObject.fromObject(tem_map).toString());

                            tem_map.clear();
                            tem_map.put(imageInfo.getVersion(), imageInfo.getCreateType());
                            imageInfo.setCreateType(JSONObject.fromObject(tem_map).toString());

                            List<String> tem_version = new ArrayList<String>();
                            tem_version.add(imageInfo.getVersion());
                            imageInfo.setVersion(JSONArray.fromObject(tem_version).toString());

                            imageInfo.setCreate_time(nowStr);
                            imageInfo.setUpdate_time(nowStr);

                            LOGGE.info("[AppStoreServiceImp Info]: Add ImageInfo" + imageInfo.toString());
                            int count = imageInfoDao.addImageInfo(imageInfo);
                            if(count > 0){
                                return APIResponse.success();
                            }
                        } else {
                            imageInfo.setApp_id(exist.getApp_id());

                            Map<String, String> tem_map = JSONObject.fromObject(exist.getV_description());
                            tem_map.put(imageInfo.getVersion(), imageInfo.getV_description());
                            imageInfo.setV_description(JSONObject.fromObject(tem_map).toString());

                            tem_map = JSONObject.fromObject(exist.getSource_url());
                            tem_map.put(imageInfo.getVersion(), EnvConst.docker_api_ip + "/"+repository.getRepo_name() + "/" + repository.getRepo_name() + ":" + imageInfo.getVersion());
                            imageInfo.setSource_url(JSONObject.fromObject(tem_map).toString());

                            tem_map = JSONObject.fromObject(exist.getMetadata());
                            tem_map.put(imageInfo.getVersion(), imageInfo.getMetadata());
                            imageInfo.setMetadata(JSONObject.fromObject(tem_map).toString());

                            tem_map = JSONObject.fromObject(exist.getCreateType());
                            tem_map.put(imageInfo.getVersion(), imageInfo.getCreateType());
                            imageInfo.setCreateType(JSONObject.fromObject(tem_map).toString());

                            List<String> versions = JSONArray.toList(JSONArray.fromObject(exist.getVersion()), String.class);
                            versions.add(imageInfo.getVersion());
                            imageInfo.setVersion(JSONArray.fromObject(versions).toString());

                            imageInfo.setUpdate_time(nowStr);
                            LOGGE.info("[AppStoreServiceImp Info]: Update ImageInfo" + imageInfo.toString());
                            int count = imageInfoDao.updateImageInfo(imageInfo);
                            if(count > 0){
                                return APIResponse.success();
                            }
                        }
                    }
                }
            }
        } else {
            LOGGE.info("[AppStoreServiceImp Error]: " + "上传镜像源文件失败，文件名为：" + originalFileName);
            return APIResponse.fail("fail");
        }
        return APIResponse.fail("fail");
    }

    /**
     * 调用dockerapi /images/load
     * @param url api的url
     * @param sourceFile 待load的镜像文件
     * @return 加载到的镜像文件名称或镜像ID
     */
    public String loadImage(String url, MultipartFile sourceFile){
        String image = "";
        try {
            LOGGE.info("[AppStoreServiceImp Info]: POST " + url);
            ResponseEntity<String> loadResponse = restTemplate.postForEntity(url, sourceFile.getBytes(), String.class);
            String msg = loadResponse.getBody().toString();
            int beginIndex = msg.indexOf("Loaded image ID");
            if(beginIndex > -1){
                beginIndex += 24;
                image = msg.substring(beginIndex, msg.length() - 6);
                LOGGE.info("[AppStoreServiceImp Info]: " + image + " Loaded");
            } else if(msg.indexOf("Loaded image") > -1){
                beginIndex = msg.indexOf("Loaded image") + 14;
                image = msg.substring(beginIndex, msg.length() - 6);
                LOGGE.info("[AppStoreServiceImp Info]: " + image + " Loaded");
            } else {
                return image;
            }
        } catch (IOException io){
            LOGGE.info("[AppStoreServiceImp Error]: " + "load镜像失败");
            io.printStackTrace();
        }
        return image;
    }

    /**
     * 调用dockerapi /images/{imageName/imageID}/tag?repo=XX&tag=XX
     * @param url api的url
     * @param imageInfo 用来获取repo和tag
     * @param repoName
     * @return
     */
    public Boolean tagImage(String url, ImageInfo imageInfo, String repoName){
        Boolean result = false;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("repo",EnvConst.docker_api_ip + "/"+ repoName + "/" + imageInfo.getAppName());//根据用户的repo来代替demo
        map.add("tag", imageInfo.getVersion());
        LOGGE.info("[AppStoreServiceImp Info]: POST " + url + "?repo=" + map.get("repo") + "&tag=" + map.get("tag"));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        String code = response.getStatusCode().toString();
        if("201".equals(code)){
            return true;
        }
        return result;
    }

    /**
     * 调用dockerapi /images/{repoName}/push?tag={tag}
     * @param url api的url
     * @param tag 标签tag
     * @return
     */
    public Boolean pushImage(String url, String tag){
        Boolean result = false;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        try {
            String authStr = "{ \"username\": \"admin\", \"password\": \"Harbor12345\", \"serveraddress\": \"132.232.140.33\" }";
            String authStr_base64 = Base64.getEncoder().encodeToString(authStr.getBytes("utf-8"));
            headers.set("X-Registry-Auth", authStr_base64);
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("tag", tag);
            LOGGE.info("[AppStoreServiceImp Info]: POST " + url + "?tag=" + map.get("tag"));
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class);
            String msg = response.getBody().toString();
            LOGGE.info(msg);
            if(response.getStatusCode().toString().equals("200") && !msg.contains("error")){
                return true;
            }
        } catch (UnsupportedEncodingException e){
            LOGGE.info("[AppStoreServiceImp Error]: " + "tag镜像失败");
            e.printStackTrace();
        } catch (HttpClientErrorException e){
            LOGGE.info("[AppStoreServiceImp Error]: " + "tag镜像失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deployImage(){

        LOGGE.info("[AppStoreServiceImp Info]: " + "GET " + EnvConst.k8s_api_prefix);
        ResponseEntity<String> test  = restTemplate.getForEntity(EnvConst.k8s_api_prefix, String.class);
        test.getStatusCode();
    }

    @Override
    public APIResponse checkAppExist(LoginingUser user, String appName, String version, String repoType){
        Repository repository = repositoryService.getRepoByUserid(user.getUser_uuid())
                .stream()
                .filter(repository1 -> repository1.getRepo_type().equals(repoType))
                .findFirst()
                .orElse(null);
        ImageInfo exist = imageInfoDao.getImages(repository.getRepo_uuid(), user.getUser_uuid())
                .stream()
                .filter(imageInfo -> imageInfo.getAppName().equals(appName))
                .findFirst()
                .orElse(null);
        if(null == exist){
            return APIResponse.success("not existed");
        } else {
            List<String> verisons = JSONArray.toList(JSONArray.fromObject(exist.getVersion()),String.class);
            if(verisons.contains(version)){
                return  APIResponse.success("existed");
            } else {
                return APIResponse.success("not existed");
            }
        }
    }
}

package com.tanli.cloud.service;

import com.tanli.cloud.dao.ImageInfoDao;
import com.tanli.cloud.dao.RepositoryDao;
import com.tanli.cloud.dao.TemplateDao;
import com.tanli.cloud.dao.UserLogDao;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.UserLog;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.model.response.Repository;
import com.tanli.cloud.utils.APIResponse;
import com.tanli.cloud.utils.FtpUtil;
import com.tanli.cloud.utils.UuidUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanli on 2018/12/1 0001.
 */
@Service
public class AppOrchServiceImp implements AppOrchService{
    @Autowired
    private FtpUtil ftpUtil;
    @Autowired
    private ImageInfoDao imageInfoDao;
    @Autowired
    private RepositoryDao repositoryDao;
    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private UserLogDao userLogDao;

    private static final Logger LOGGE = LoggerFactory.getLogger(AppOrchServiceImp.class);

    @Override
    public APIResponse getImageInfo(User user) {
        String userid = user.getUser_uuid();
        List<Repository> repositories = repositoryDao.getRepoByUserid(userid);
        List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
        repositories.forEach(repository -> {
            List<ImageInfo> temp = imageInfoDao.getImages(repository.getRepo_uuid(), userid);
            temp.forEach(imageInfo -> {
                imageInfo.setType(repository.getRepo_type());
            });
            imageInfos.addAll(temp);
        });
        return APIResponse.success(imageInfos);
    }

    @Override
    public APIResponse addTemplate(User user, Template template, MultipartFile file) {
        //上传logo到FTP中
        String originalFileName = file.getOriginalFilename();//必须使用originFileName
        String[] temp = originalFileName.split("\\.");
        String newFileName = template.getTemplateName() + "." + temp[temp.length-1];
        String logoUrl = uploadFile(originalFileName, file, newFileName);
        if(!(logoUrl.equals(""))){
            template.setLogo_url(logoUrl);
            //保存数据到数据库中
            template.setUuid(UuidUtil.getUUID());
            template.setUserId(user.getUser_uuid());
            DateTime now = DateTime.now();
            String nowStr = now.getYear()+"-"+now.getMonthOfYear()+"-"+now.getDayOfMonth()+" "+ now.getHourOfDay() + ":"+now.getMinuteOfHour()+":"+now.getSecondOfMinute();
            template.setCreate_time(nowStr);
            template.setUpdate_time(nowStr);
            //操作日志
            UserLog userLog = new UserLog();
            userLog.setUuid(UuidUtil.getUUID());
            userLog.setUser_id(user.getUser_uuid());
            userLog.setUsername(user.getUserName());
            userLog.setResoureType("Template");
            userLog.setResourceId(template.getUuid());
            userLog.setOperation("增加应用模板");
            userLog.setIsDeleted("0");
            userLog.setCreate_time(nowStr);
            userLogDao.addUserLog(userLog);
            int count = templateDao.addTemplate(template);
            if(count > 0){
                return APIResponse.success();
            } else {
                LOGGE.info("[AppOrchServiceImp Error]: " + "保存模板到数据库失败");
                return APIResponse.fail("保存模板到数据库失败");
            }
        } else {
            LOGGE.info("[AppOrchServiceImp Error]: " + "上传logo文件失败");
            return APIResponse.fail("上传logo文件失败");
        }
    }

    private String uploadFile(String fileName, MultipartFile file, String newFileName) {
        String result = "";
        String path = "/templatelogo";
        try {
            InputStream inputStream = file.getInputStream();
            result = ftpUtil.uploadFile(fileName, inputStream, path, newFileName);
        } catch (IOException e){
            LOGGE.info("[AppOrchServiceImp Error]: " + fileName + "转换失败");
        }
        return result;
    }
}

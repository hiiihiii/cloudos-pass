package com.tanli.cloud.controller.appcenter;

import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.service.AppStoreService;
import com.tanli.cloud.service.ImageInfoService;
import com.tanli.cloud.service.NodeService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@EnableAutoConfiguration
@Controller
@RequestMapping("appcenter")
public class AppstoreController {

    @Autowired
    private AppStoreService appStoreService;
    @Autowired
    private ImageInfoService imageInfoService;


    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appcenter/appstore");
    }

    @PostMapping("upload")
    @ResponseBody
    public APIResponse uploadImage(HttpServletRequest request, ImageInfo imageInfo,
                                   @RequestParam(value = "logoFile", required = true)MultipartFile logoFile,
                                   @RequestParam(value = "sourceFile", required = true)MultipartFile sourceFile) {
        LoginingUser user = (LoginingUser) request.getSession().getAttribute("login_user");
        imageInfo.setUser_id(user.getUser_uuid());

        return appStoreService.uploadImage(imageInfo, logoFile, sourceFile, user);
    }

    @RequestMapping("appinfo")
    @ResponseBody
    /**
     * 获取镜像或镜像模板信息
     * repoType: public/private
     * appType: image/template/all
     */
    public APIResponse getAppInfo(HttpServletRequest request, String repoType, String appType){
        //appStoreService.deployImage();
        LoginingUser user = (LoginingUser) request.getSession().getAttribute("login_user");
        if("all".equals(appType)){

        } else if("image".equals(appType)){
            return imageInfoService.getImages(user, repoType, appType);
        } else {

        }

        return null;
    }
}

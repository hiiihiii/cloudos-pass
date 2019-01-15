package com.tanli.cloud.controller.appcenter;

import com.tanli.cloud.model.DeployedImage;
import com.tanli.cloud.model.DeployedTemplate;
import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.service.AppDeployService;
import com.tanli.cloud.service.AppStoreService;
import com.tanli.cloud.service.ImageInfoService;
import com.tanli.cloud.service.TemplateService;
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
    @Autowired
    private AppDeployService appDeployService;


    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appcenter/appstore");
    }

    @PostMapping("upload")
    @ResponseBody
    public APIResponse uploadImage(HttpServletRequest request, ImageInfo imageInfo,
                                   @RequestParam(value = "logoFile", required = true)MultipartFile logoFile,
                                   @RequestParam(value = "sourceFile", required = true)MultipartFile sourceFile) {
        User user = (User) request.getSession().getAttribute("login_user");
        imageInfo.setUser_id(user.getUser_uuid());

        return appStoreService.uploadImage(imageInfo, logoFile, sourceFile, user);
    }

    @RequestMapping("imageinfo")
    @ResponseBody
    /**
     * 获取镜像或镜像模板信息
     * repoType: public/private
     */
    public APIResponse getAppInfo(HttpServletRequest request, String repoType){
        User user = (User) request.getSession().getAttribute("login_user");
        return imageInfoService.getImages(user, repoType);

    }

    @RequestMapping("templateinfo")
    @ResponseBody
    public APIResponse getTemplateInfo(HttpServletRequest request, String repoType){
        User user = (User) request.getSession().getAttribute("login_user");
         return appStoreService.getTemplates(user, repoType);
    }

    @RequestMapping("checkexist")
    @ResponseBody
    /**
     * 查询同名同版本的镜像是否已经存在
     * appName: 镜像名称
     * version: 版本号
     * repoType: public/private
     */
    public APIResponse checkAppExist(HttpServletRequest request, String appName, String version, String repoType){
        User user = (User) request.getSession().getAttribute("login_user");
        return appStoreService.checkAppExist(user, appName, version, repoType);
    }

    @PostMapping("image/deploy")
    @ResponseBody
    public APIResponse deployImage (HttpServletRequest request, DeployedImage deployedImage){
        User user = (User) request.getSession().getAttribute("login_user");
        appDeployService.deployImage(user, deployedImage);
        return null;
    }

    @PostMapping("template/deploy")
    @ResponseBody
    public APIResponse deployTemplate (HttpServletRequest request, DeployedTemplate deployedTemplate) {
        User user = (User) request.getSession().getAttribute("login_user");
        return null;
    }
}

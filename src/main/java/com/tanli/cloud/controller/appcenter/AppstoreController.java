package com.tanli.cloud.controller.appcenter;

import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.service.AppStoreService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

@EnableAutoConfiguration
@Controller
@RequestMapping("appcenter")
public class AppstoreController {

    @Autowired
    private AppStoreService appStoreService;

    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appcenter/appstore");
    }

    @PostMapping("upload")
    public APIResponse uploadImage(ImageInfo imageInfo,
                                   @RequestParam(value = "logoFile", required = true)MultipartFile logoFile,
                                   @RequestParam(value = "sourceFile", required = true)MultipartFile sourceFile) {
        appStoreService.uploadImage(imageInfo, logoFile, sourceFile);
        return null;
    }

}

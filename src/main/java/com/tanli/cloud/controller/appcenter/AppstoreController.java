package com.tanli.cloud.controller.appcenter;

import com.tanli.cloud.model.ImageInfo;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@EnableAutoConfiguration
@Controller
@RequestMapping("appcenter")
public class AppstoreController {

    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appcenter/appstore");
    }

    @PostMapping("upload")
    public APIResponse uploadImage(ImageInfo imageInfo){
        return null;
    }

}

package com.tanli.cloud.controller.appcenter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
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
}

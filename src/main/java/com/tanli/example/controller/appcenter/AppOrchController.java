package com.tanli.example.controller.appcenter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tanli on 2018/7/10 0010.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("apporch")
public class AppOrchController {
    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appcenter/apporch");
    }
}

package com.tanli.example.controller.appmanage;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tanli on 2018/7/10 0010.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("application")
public class ApplicationController {
    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appmanage/application");
    }
}

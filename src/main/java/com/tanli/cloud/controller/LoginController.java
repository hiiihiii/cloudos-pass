package com.tanli.cloud.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@EnableAutoConfiguration
@Controller
public class LoginController {
    @RequestMapping({"/","/index", "/login"})
    @ResponseBody
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        return new ModelAndView("login");
    }
}

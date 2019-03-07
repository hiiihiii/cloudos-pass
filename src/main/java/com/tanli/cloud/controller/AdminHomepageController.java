package com.tanli.cloud.controller;

import com.tanli.cloud.model.response.User;
import com.tanli.cloud.service.AdminHomepageService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@EnableAutoConfiguration
@Controller
@RequestMapping("adminhomepage")
public class AdminHomepageController {
    @Autowired
    private AdminHomepageService adminHomepageService;

    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("homepage/adminhomepage");
    }

    @RequestMapping("images")
    @ResponseBody
    public APIResponse getAllImageInfo(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("login_user");
        return adminHomepageService.getAllImages(user);
    }

    @RequestMapping("templates")
    @ResponseBody
    public APIResponse getAllTemplate(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("login_user");
        return adminHomepageService.getAllTemplates(user);
    }
}

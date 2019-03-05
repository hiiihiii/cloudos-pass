package com.tanli.cloud.controller.appmanage;

import com.tanli.cloud.model.response.User;
import com.tanli.cloud.service.AppDeployService;
import com.tanli.cloud.service.ApplicationService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tanli on 2018/7/10 0010.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private AppDeployService appDeployService;

    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appmanage/application");
    }

    @RequestMapping(value = "info")
    @ResponseBody
    public APIResponse getApplications(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("login_user");
        return applicationService.getApplications(user);
    }

    @RequestMapping(value = "serviceInfo")
    @ResponseBody
    public APIResponse getServiceInfo(HttpServletRequest request, String deployId) {
        User user = (User) request.getSession().getAttribute("login_user");
        return applicationService.getServiceInfo(user, deployId);
    }

    @RequestMapping(value = "stop")
    @ResponseBody
    public APIResponse stop(HttpServletRequest request, String deploymentId){
        User user = (User) request.getSession().getAttribute("login_user");
        return appDeployService.stopApp(user, deploymentId);
    }

    @RequestMapping(value = "start")
    @ResponseBody
    public APIResponse start(HttpServletRequest request, String deploymentId) {
        User user = (User) request.getSession().getAttribute("login_user");
        return appDeployService.startApp(user, deploymentId);
    }
}

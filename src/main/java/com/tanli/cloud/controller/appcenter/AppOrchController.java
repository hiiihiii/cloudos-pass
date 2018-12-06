package com.tanli.cloud.controller.appcenter;

import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.service.AppOrchService;
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

/**
 * Created by tanli on 2018/7/10 0010.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("apporch")
public class AppOrchController {
    @Autowired
    private AppOrchService appOrchService;
    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appcenter/apporch");
    }

    @RequestMapping("template")
    public ModelAndView toTemplate(){
        return new ModelAndView("appcenter/template");
    }

    @RequestMapping("appinfo")
    @ResponseBody
    public APIResponse getImageInfo(HttpServletRequest request){
        LoginingUser user = (LoginingUser) request.getSession().getAttribute("login_user");
        return appOrchService.getImageInfo(user);
    }
    @PostMapping("template/add")
    @ResponseBody
    public APIResponse addTemplate(HttpServletRequest request, Template template,
                                   @RequestParam(value = "logoFile", required = true)MultipartFile logofile){
        LoginingUser user = (LoginingUser) request.getSession().getAttribute("login_user");
        return appOrchService.addTemplate(user, template, logofile);
    }
}

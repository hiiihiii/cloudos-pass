package com.tanli.cloud.controller.appcenter;

import com.tanli.cloud.model.Template;
import com.tanli.cloud.model.response.User;
import com.tanli.cloud.service.AppOrchService;
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
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanli on 2018/7/10 0010.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("apporch")
public class AppOrchController {
    @Autowired
    private AppOrchService appOrchService;
    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = {"/",""})
    public ModelAndView index(){
        return new ModelAndView("appcenter/apporch");
    }

    @RequestMapping("template")
    public ModelAndView toTemplate(){
//        return new ModelAndView("appcenter/template");
        return new ModelAndView("appcenter/template_new");
    }

    @RequestMapping("appinfo")
    @ResponseBody
    public APIResponse getImageInfo(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("login_user");
        return appOrchService.getImageInfo(user);
    }

    @PostMapping("template/add")
    @ResponseBody
    public APIResponse addTemplate(HttpServletRequest request, Template template,
                                   @RequestParam(value = "logoFile", required = true)MultipartFile logofile){
        User user = (User) request.getSession().getAttribute("login_user");
        return appOrchService.addTemplate(user, template, logofile);
    }

    @RequestMapping("templateinfo")
    @ResponseBody
    public APIResponse getTemplate(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("login_user");
        return templateService.getTemplate(user);
    }

    @RequestMapping("templatedetail")
    @ResponseBody
    public ModelAndView templateDetail(){
        return new ModelAndView("appcenter/template_detail");
    }

    @RequestMapping("templateinfo/detail")
    @ResponseBody
    public APIResponse getTemplateDetail(HttpServletRequest request, String templateId){
        User user = (User) request.getSession().getAttribute("login_user");
        return templateService.getTemplateDetail(user, templateId);
    }

    @PostMapping("publish")
    @ResponseBody
    public APIResponse publish (HttpServletRequest request, String templateId){
        User user = (User) request.getSession().getAttribute("login_user");
        return templateService.publishTemplate(user, templateId);
    }

    @PostMapping("delete")
    @ResponseBody
    public APIResponse delete (HttpServletRequest request,
                               @RequestParam(value = "ids[]") String[] ids) {
        User user = (User) request.getSession().getAttribute("login_user");
        return templateService.deleteByIds(user, ids);
    }
}

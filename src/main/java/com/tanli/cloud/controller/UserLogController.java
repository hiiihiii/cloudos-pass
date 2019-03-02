package com.tanli.cloud.controller;

import com.tanli.cloud.model.response.User;
import com.tanli.cloud.service.UserLogService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2019/3/2 0002.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("userlog")
public class UserLogController {
    @Autowired
    private UserLogService userLogService;

    @RequestMapping(value = {"/", ""})
    public ModelAndView index(){
        return new ModelAndView("system/userlog");
    }

    @RequestMapping("info")
    @ResponseBody
    public APIResponse getLogs(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("login_user");
        return userLogService.getLogs(user);
    }

    @PostMapping("delete")
    @ResponseBody
    public APIResponse deleteLog(HttpServletRequest request,
                                 @RequestParam(value = "ids[]") String[] ids){
        User user = (User) request.getSession().getAttribute("login_user");
        return userLogService.deleteLogs(user,ids);
    }
}

package com.tanli.cloud.controller;

import com.tanli.cloud.model.User;
import com.tanli.cloud.model.response.LoginingUser;
import com.tanli.cloud.service.UserManageService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired
    private UserManageService userManageService;

    @RequestMapping({"/","/index", "/login"})
    @ResponseBody
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        return new ModelAndView("login");
    }

    @PostMapping("/loginIn")
    @ResponseBody
    public APIResponse loginVerify(HttpServletRequest request, String username, String password){
        User user = new User();
        user.setPassword(password);
        user.setUserName(username);
        LoginingUser userInfo = userManageService.loginVerify(user);
        if(null != userInfo){
            request.getSession().setAttribute("login_user", userInfo);
            return APIResponse.success(userInfo);
        } else {
            return APIResponse.fail("fail");
        }
    }
}

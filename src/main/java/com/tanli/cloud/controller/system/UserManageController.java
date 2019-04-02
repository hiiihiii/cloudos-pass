package com.tanli.cloud.controller.system;

import com.tanli.cloud.model.response.User;
import com.tanli.cloud.service.UserManageService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("/user")
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    @RequestMapping({"", "/"})
    public ModelAndView userIndex(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("system/user");
    }

    @RequestMapping("info")
    @ResponseBody
    public APIResponse getUsers(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("login_user");
        return userManageService.getUsers(user);
    }

    @RequestMapping("checkUserName")
    @ResponseBody
    public APIResponse checkUserName(HttpServletRequest request, String username) {
        return userManageService.checkUserName(username);
    }
}

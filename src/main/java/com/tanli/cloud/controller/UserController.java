package com.tanli.cloud.controller;

import com.tanli.cloud.model.response.User;
import com.tanli.cloud.service.UserManageService;
import com.tanli.cloud.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@EnableAutoConfiguration
@Controller
public class UserController {
    @Autowired
    private UserManageService userManageService;

    @GetMapping({"/", "/index"})
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    @ResponseBody
    public APIResponse loginVerify(HttpServletRequest request, String username, String password){
        User user = new User();
        user.setPassword(password);
        user.setUserName(username);
        User userInfo = userManageService.loginVerify(user);
        if(null != userInfo){
            request.getSession().setAttribute("login_user", userInfo);
            return APIResponse.success(userInfo);
        } else {
            return APIResponse.fail("fail");
        }
    }

    @PostMapping("user/add")
    @ResponseBody
    public APIResponse addUser(HttpServletRequest request, User user){
        return userManageService.addUser(user);
    }

    @PostMapping("user/delete")
    @ResponseBody
    public APIResponse deleteUser(HttpServletRequest request,
                                  @RequestParam(value = "ids[]") String[] ids){
        User user =(User) request.getSession().getAttribute("login_user");
        return userManageService.deleteByIds(user, ids);
    }
}

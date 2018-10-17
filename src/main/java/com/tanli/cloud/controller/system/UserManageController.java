package com.tanli.cloud.controller.system;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("/user")
public class UserManageController {

    @RequestMapping({"", "/"})
    public ModelAndView userIndex(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("system/user");
    }

}

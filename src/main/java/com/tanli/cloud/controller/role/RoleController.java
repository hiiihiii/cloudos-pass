package com.tanli.cloud.controller.role;

import com.tanli.cloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanli on 2018/10/15 0015.
 */
@EnableAutoConfiguration
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    public RoleService roleService;

    @RequestMapping({"", "/"})
    public ModelAndView roleIndex(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("system/role");
    }

    @RequestMapping("/add")
    public void addRole(HttpServletRequest httpServletRequest){
//        Role role = new Role();
//        role.setUuid("550e8400-e19b-41d4-a716-446655440000");
//        role.setName("public_user");
//        role.setDescription("普通用户");
//        String tsStr = "2011-05-09 11:49:45";
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//        role.setCreate_time(ts);
//        role.setUpdate_time(ts);
//
//        roleService.addRole(role);
    }
}

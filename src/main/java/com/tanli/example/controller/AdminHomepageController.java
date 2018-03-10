package com.tanli.example.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@EnableAutoConfiguration
@Controller
@RequestMapping("adminhomepage")
public class AdminHomepageController {

    @RequestMapping(value = "/")
    public ModelAndView index(){
        return new ModelAndView("homepage/adminhomepage");
    }
}

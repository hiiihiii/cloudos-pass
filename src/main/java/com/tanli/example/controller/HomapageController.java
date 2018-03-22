package com.tanli.example.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "homepage")
public class HomapageController {

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView adminhomepage(){
//        return new ModelAndView("homepage/adminhomepage");
        return null;
    }
}

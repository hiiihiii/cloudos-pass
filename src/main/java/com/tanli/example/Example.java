package com.tanli.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/8/26 0026.
 */
@Controller
@EnableAutoConfiguration

public class Example {

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}

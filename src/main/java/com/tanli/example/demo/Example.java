package com.tanli.example.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/8/26 0026.
 */
@Controller
@EnableAutoConfiguration

public class Example {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
}

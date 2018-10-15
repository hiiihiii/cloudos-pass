package com.tanli.cloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018/3/8 0008.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /*自定义资源映射*/
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //将所有/static/** 访问都映射到classpath:/static/ 目录下
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/static/**");
//        super.addInterceptors(registry);
//    }
}

package com.tanli.cloud.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by tanli on 2018/10/16 0016.
 * 让自定义拦截器生效
 */
@Component
public class AdapterConfig extends WebMvcConfigurerAdapter {
    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}

package com.tanli.cloud.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tanli on 2018/10/16 0016.
 * 自定义登录验证拦截
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger LOGGE = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)
        throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        //LOGGE.info("[LoginInterceptor Error]: basepath " + basePath);
        LOGGE.info("[LoginInterceptor Info]: path " + path);

         if(!doLoginInterceptor(path, basePath)){
            return true;
        }
        HttpSession session = request.getSession();
        if(session.getAttribute("login_user") == null){
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        } else {
            session.setAttribute("login_user",session.getAttribute("login_user"));
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean doLoginInterceptor(String path, String basePath){
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();
        notLoginPaths.add("");
        notLoginPaths.add("/");
        notLoginPaths.add("/index");
        notLoginPaths.add("/login");
        notLoginPaths.add("/loginIn");
        if(notLoginPaths.contains(path)){
            return false;
        } else {
            return true;
        }
    }
}

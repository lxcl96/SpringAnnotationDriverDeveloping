package com.ly.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:MyInterceptor.class
 * Author:ly
 * Date:2022/7/21
 * Description:拦截器
 */
public class MyInterceptor implements HandlerInterceptor {
    /**
     *
     * 拦截器目标方法执行前执行
     * @param request request请求
     * @param response  response请求
     * @param handler
     * @return 是否放行？返回true代表放行
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor...preHandle... url=" + request.getRequestURL());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //目标方法正确执行后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor...postHandle...");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //页面响应后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyInterceptor...afterCompletion...");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

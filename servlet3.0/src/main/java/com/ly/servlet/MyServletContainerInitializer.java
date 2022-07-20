package com.ly.servlet;

import com.ly.service.HelloService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/**
 * FileName:MyServletContainerInitializer.class
 * Author:ly
 * Date:2022/7/20
 * Description:
 */
@HandlesTypes(value = {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     *
     * @param set 为注解@HandlesTypes(value = {})中指定的要处理类类型（包括接口的子接口，实现类等）
     * @param servletContext 应用上下文对象，一个web应用一个servletContext
     * @throws ServletException servlet异常
     */
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        Iterator<Class<?>> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        //向servletContext注册三大组件
        ServletRegistration.Dynamic userServlet = servletContext.addServlet("UserServlet", UserServlet.class);
        //servlet绑定映射信息
        userServlet.addMapping("/tomcat");

        //filter绑定映射信息
        FilterRegistration.Dynamic userFilter = servletContext.addFilter("UserFilter", UserFilter.class);
        userFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");


        servletContext.addListener(UserListener.class);
        System.out.println("MyServletContainerInitializer 启动");
    }
}

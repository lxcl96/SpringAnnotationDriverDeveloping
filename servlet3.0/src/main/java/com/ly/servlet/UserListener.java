package com.ly.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * FileName:UserListener.class
 * Author:ly
 * Date:2022/7/20
 * Description: 项目启动和停止
 */
public class UserListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("UserListener...ServletContextListener...initial");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
//        servletContext.addServlet();
//        servletContext.addFilter();
//        servletContext.addListener();
        System.out.println("UserListener...ServletContextListener...destroy");
    }
}

package com.ly.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FileName:HelloServlet.class
 * Author:ly
 * Date:2022/7/20
 * Description: 最基础的servlet，默认下同步请求
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    /*
        默认情况下每一个请求都是一个单独的线程，每次请求过来都会从线程池拿取新的线程，等到请求运行完成才会把线程放回线程池
        http-nio-8080-exec-4......begin....
        http-nio-8080-exec-4......processing....
        http-nio-8080-exec-4......end....

        如果高并发的情况会出现线程池不够用，所以Servlet3.0执行异步请求 HelloAsyncServlet.java
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(Thread.currentThread().getName() + "......begin....");
        sayHello();
        resp.getWriter().write("hello...");
        System.out.println(Thread.currentThread().getName() + "......end....");
    }

    public void sayHello() {
        System.out.println(Thread.currentThread().getName() + "......processing....");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

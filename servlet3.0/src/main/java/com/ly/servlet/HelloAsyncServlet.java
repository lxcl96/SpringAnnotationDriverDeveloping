package com.ly.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FileName:HelloAsyncServlet.class
 * Author:ly
 * Date:2022/7/21
 * Description:与HelloServlet区分，是异步请求
 */
@WebServlet(value = "/async",asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(Thread.currentThread().getName() + "......begin....");
        //1、支持异步模式，属性`asyncSupported = true`

        //2、开启异步模式
        AsyncContext asyncContext = req.startAsync();

        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                //处理正常结束逻辑
                System.out.println("***正常结束1");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                //处理超时逻辑
                System.out.println("***超时2");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                //处理出现错误时逻辑
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                //处理异步请求逻辑【异步线程逻辑】
                System.out.println("***异步线程启动3");
            }
        });
        //设置超时事件
        asyncContext.setTimeout(10000L);
        //3、业务逻辑进行异步处理----开始异步处理
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "......begin....");
                sayHello(); //异步线程调用

                //5、获取到asyncContext其实就是前面的asyncContext 【必须放在complete方法前】
                AsyncContext reqAsyncContext = req.getAsyncContext();
                //4、业务逻辑异步处理完成，设定完成标记
                asyncContext.complete();//表示异步处理完成

                try {
                    //6、AsyncContext获取响应，并回应
                    reqAsyncContext.getResponse().getWriter().write("hello async");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "......end....");
            }
        });
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

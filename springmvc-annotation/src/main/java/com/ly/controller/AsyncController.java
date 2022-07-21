package com.ly.controller;

import com.ly.service.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * FileName:AsyncController.class
 * Author:ly
 * Date:2022/7/21
 * Description: Spring中使用servlet3.0的异步请求
 */

@Controller
public class AsyncController {


    /**
     * 通过Spring使用异步请求
     *  如果控制器返回的是Callable
     *      1、SpringMVC会启动异步处理，并把返回的Callable提交给一个TaskExecutor，TaskExecutor会在另外一个线程中处理
     *      2、此时DispatcherServlet和所有的过滤器Filter将会退出Servlet容器线程（就是请求线程），但是response请求会被保留下用于返回（处于open状态）
     *      3、Callable返回结果，SpringMVC将请求重新派发给Servlet容器，恢复之前的处理
     *      4、DispatcherServlet会被再次调用，并且根据Callable的返回值，SpringMVC继续执行视图渲染操作等（从头再执行的，相当于调用两次只是不再执行控制器方法了）

                ======================执行流程===============================
                 MyInterceptor...preHandle... url=http://localhost:8080/springmvc-anno/async01
                 primary Thread ---http-nio-8080-exec-4   start   =====>1658390762558
                 primary Thread ---http-nio-8080-exec-4   end   =====>1658390762561
                ===================1、DispatcherServlet和所有的Filter此时会退出↑   等待Callable执行↓=============
                 second Thread ---MvcAsync1   start   =====>1658390762575
                 second Thread ---MvcAsync1   end   =====>1658390765577
                ================2、Callable执行完毕并返回↑，并把返回结果再次派发出去↓============================
                 MyInterceptor...preHandle... url=http://localhost:8080/springmvc-anno/async01
                 MyInterceptor...postHandle...
                 MyInterceptor...afterCompletion...
                ===============3、DispatcherServlet被再次调用，接收请求↑（Callable返回值就是目标方法的返回值，所有不会再调用控制器方法了直接执行postHandle）==================
                ===============4、渲染，response=============

     *      5、异步请求的拦截器
     *         1、可以使用Servlet3.0中AsyncContext中原生的监听器/拦截器AsyncListener 拦截异步请求
     *         2、使用SpringMVC下的异步请求拦截器AsyncHandlerInterceptor接口
     * @return callable 异步执行
     */
    @ResponseBody
    @RequestMapping("/async01")
    public Callable<String> async01() {
        System.out.println("primary Thread ---" + Thread.currentThread().getName() + "   start   =====>" + System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println("second Thread ---" + Thread.currentThread().getName() + "   start   =====>" + System.currentTimeMillis());
                Thread.sleep(3000);
                System.out.println("second Thread ---" + Thread.currentThread().getName() + "   end   =====>" + System.currentTimeMillis());
                return "Callable<String> async01()";
            }
        };
        System.out.println("primary Thread ---" + Thread.currentThread().getName() + "   end   =====>" + System.currentTimeMillis());

        return callable;
    }


    /**
     * 接收到创建订单请求，但是当前方法没有权限创建订单(或者并不急于处理)，则把消息保存下来等待有权限的方法调用
     * @return 延迟结果
     */
    @ResponseBody
    @RequestMapping("/createOrder")
    public DeferredResult<Object> createOrder() {
        //设置超时信息
        DeferredResult<Object> deferredResult = new DeferredResult<>(30000L,"timeout,order create failed!");
        //将创建订单信息抛出
        DeferredResultQueue.save(deferredResult);
        return deferredResult;
    }

    @ResponseBody
    //真正创建订单后触发，返回订单信息
    @RequestMapping("/create")
    public String create() {
        //创建订单
        String order = UUID.randomUUID().toString();
        DeferredResult<Object> deferredResult = DeferredResultQueue.get();
        deferredResult.setResult(order);
        return "success ===>" + order;
    }
}

package com.ly.controller;

import com.ly.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FileName:HelloController.class
 * Author:ly
 * Date:2022/7/21
 * Description:
 */
@Controller
public class HelloController {
    @Autowired
    private HelloService helloService;


    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return helloService.sayHello("ly");
    }

    @RequestMapping("/suc")
    //视图解析器自动解析
    public String success() {
        return "success";
    }

}

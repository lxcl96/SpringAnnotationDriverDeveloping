package com.ly.service;

import org.springframework.stereotype.Service;

/**
 * FileName:HelloService.class
 * Author:ly
 * Date:2022/7/21
 * Description:
 */
@Service
public class HelloService {

    public String sayHello(String name) {
        return "hello  " + name;
    }
}

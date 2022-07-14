package com.ly.ext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * FileName:UserService.class
 * Author:ly
 * Date:2022/7/14
 * Description:
 */
@Service
public class UserService {

    @EventListener({ApplicationEvent.class})
    public void login(ApplicationEvent event) {
        //提供自动注入，获取到IOC容器和事件
        System.out.println("UserService监听到事件：" + event);
    }
}

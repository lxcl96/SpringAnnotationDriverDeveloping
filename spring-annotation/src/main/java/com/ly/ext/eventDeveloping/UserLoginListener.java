package com.ly.ext.eventDeveloping;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * FileName:UserRegisterListener.class
 * Author:ly
 * Date:2022/7/13
 * Description:
 */
@Component
public class UserLoginListener implements ApplicationListener<UserOnLineEvent> {
    @Override
    public void onApplicationEvent(UserOnLineEvent event) {
        System.out.println("收到用户登陆事件" + event + " ，准备进行业务处理！");
    }
}

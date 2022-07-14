package com.ly.ext.eventDeveloping;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * FileName:UserController.class
 * Author:ly
 * Date:2022/7/13
 * Description:
 */
@Controller
public class UserController {

    public void login(ApplicationContext applicationContext) {
        System.out.println("用户登陆，发布用户登陆事件！");

        applicationContext.publishEvent(new UserOnLineEvent(this));
        //其他的业务
    }

}

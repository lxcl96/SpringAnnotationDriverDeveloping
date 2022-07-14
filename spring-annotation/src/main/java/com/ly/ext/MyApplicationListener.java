package com.ly.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * FileName:MyApplicationListener.class
 * Author:ly
 * Date:2022/7/13
 * Description: ApplicationListener监听ApplicationEvent事件
 */
@Component
//指定的泛型为哪个事件类型，IOC容器就会只监听哪个事件（包括子类） ，最大级别为 ApplicationEvent 监听所有事件
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    //当容器中发布此事件以后，该方法才会触发 ，参数类型必须和泛型完全一致
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("MyApplicationListener 事件" + event + "触发,timestamp=" + event.getTimestamp());
        Object source = event.getSource();
        System.out.println(source);
    }


}

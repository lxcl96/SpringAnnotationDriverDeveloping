package com.ly.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * FileName：Dog.java
 * Author：Ly
 * Date：2022/7/3
 * Description：
 */
public class Dog  implements ApplicationContextAware{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Dog() {
        System.out.println(dateFormat.format(new Date()) + " 一个dog对象被创建");
    }

    /**
     * dog对象创建并复制之后调用
     */
    @PostConstruct
    public void init() {
        System.out.println(dateFormat.format(new Date()) + "  dog对象 init");
    }


    /**
     * 对象从IOC中移除前调用此方法
     */
    @PreDestroy
    public void destroy() {
        System.out.println(dateFormat.format(new Date()) + "  dog对象 destroy");
    }

    /**
     * 通过ApplicationContextAware接口 获取IOC容器,这个功能就是ApplicationContextAwareProcessor类实现的
     * @param applicationContext IOC容器
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getEnvironment();
        //applicationContext.getBean("");
    }
}

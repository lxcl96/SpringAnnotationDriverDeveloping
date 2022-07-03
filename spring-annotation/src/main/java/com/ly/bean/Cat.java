package com.ly.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName：Cat.java
 * Author：Ly
 * Date：2022/7/3
 * Description：
 */
public class Cat implements InitializingBean, DisposableBean {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Cat() {
        System.out.println(dateFormat.format(new Date()) + " 一个cat对象被创建");

    }

    /**
     * bean对象销毁方法
     * @throws Exception 异常
     */
    @Override
    public void destroy() throws Exception {
        System.out.println(dateFormat.format(new Date()) + "  car对象 destroy");
    }

    /**
     * bean对象初始化方案
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(dateFormat.format(new Date()) + "  car对象 init");
    }
}

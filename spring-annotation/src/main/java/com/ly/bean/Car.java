package com.ly.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName：Car.java
 * Author：Ly
 * Date：2022/7/3
 * Description：
 */
public class Car {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Car() {
        System.out.println(dateFormat.format(new Date()) + " 一个car对象被创建");
    }


    /**
     * 自定义Car的初始化方法 （不能有参数）
     */
    public void init() {
        System.out.println(dateFormat.format(new Date()) + "  car对象 init");
    }


    /**
     * 自定义Car销毁方法 （不能有参数）
     */
    public void destroy() {
        System.out.println(dateFormat.format(new Date()) + "  car对象 destroy");
    }
}

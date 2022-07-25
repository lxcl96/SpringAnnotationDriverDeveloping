package com.ly.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FileName:Bos.class
 * Author:ly
 * Date:2022/7/5
 * Description:
 */

/**
 * 注解@Autowire可以标注在：
 *      字段：不推荐
 *      方法：一般为set方法
 *      参数：形参
 *      构造器：
 *      其他注解：
 */
@Component
public class Boss {

    //@Autowired
    private Car car;

//    public Boss() {
//    }

    ///@Autowired
    public Boss(Car car) {
        System.out.println("只有有参构造器，但是设置了自动注入，所以不会报错！");
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    //@Autowired
    //@Autowire标注在方法上，方法有形参时，Spring容器创建当前对象Boss时就会调用被注解Autowire标注的方法，完成赋值
    //方法使用的参数，自定义类型的值从IOC容器中取,如果IOC容器中没有，就会报错。
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}

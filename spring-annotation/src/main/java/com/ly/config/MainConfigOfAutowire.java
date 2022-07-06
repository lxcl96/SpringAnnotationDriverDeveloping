package com.ly.config;

import com.ly.bean.Car;
import com.ly.bean.Color;
import com.ly.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * FileName:MainConfigOfAutowire.class
 * Author:ly
 * Date:2022/7/4
 * Description: @Autowire注解
 */
@ComponentScan({"com.ly.controller","com.ly.dao","com.ly.service","com.ly.bean"})
@Configuration
public class MainConfigOfAutowire {

    @Primary //默认首选bookDao2这个组件
    @Bean("bookDao2")
    public BookDao getBookDao(){
        return new BookDao();
    }

    //@Primary
    @Bean("car2")
    public Car getCar() {
        return new Car();
    }


    @Bean("color")//@Autowire可以不写
    public Color getColor(//@Autowired
                                      Car car) {
        Color color = new Color();
        color.setCar(car);//只是会自定注入形参，并不是和”@Autowire标注在set方法上，创建对象时会自动调用set方法“一样，记住了。
        return color;
    }
}

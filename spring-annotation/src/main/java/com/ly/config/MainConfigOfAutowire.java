package com.ly.config;

import com.ly.dao.BookDao;
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
@ComponentScan({"com.ly.controller","com.ly.dao","com.ly.service"})
@Configuration
public class MainConfigOfAutowire {

    @Primary //默认首选bookDao2这个组件
    @Bean("bookDao2")
    public BookDao getBookDao(){
        return new BookDao();
    }
}

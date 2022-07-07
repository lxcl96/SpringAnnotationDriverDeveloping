package com.ly.config;

import com.ly.aop.LogAspects;
import com.ly.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * FileName:MainConfigurationOfAOP.class
 * Author:ly
 * Date:2022/7/7
 * Description:AOP的注解实现
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigurationOfAOP {


    @Bean//业务逻辑类加入到容器中
    public MathCalculator calculator(){
        return new MathCalculator();
    }


    @Bean//切面类加入到容器中
    public LogAspects logAspects() {
        return new LogAspects();
    }
}

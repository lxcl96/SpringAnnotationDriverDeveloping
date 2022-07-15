package com.ly.ext;

import com.ly.bean.Blue;
import com.ly.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * FileName:ExtConfig.class
 * Author:ly
 * Date:2022/7/13
 * Description: Spring的扩展原理
 */


/**
 * Spring扩展原理：
 *  1、BeanFactoryPostProcessor
 *      [区别BeanFactoryPostProcessor bean的后置处理器，在bean被创建，及初始化前后拦截调用的]
 *      beanFactory的后置处理器，在bean标准初始化后调用（此时所有bean定义被加载到IOC容器中，但是并没有任何bean被实例化）
 */
@Configuration
@Import(Cat.class) //放在MyBeanFactoryPostProcessor前，用来判断 BeanFactoryPostProcessor 的调用时机
@ComponentScan({"com.ly.ext"})
public class ExtConfig {
    @Bean
    public Blue blue() {
        return new Blue();
    }
}

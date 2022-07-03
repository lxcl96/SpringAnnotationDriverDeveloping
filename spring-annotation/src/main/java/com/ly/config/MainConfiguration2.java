package com.ly.config;

import com.ly.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Author : Ly
 * Date : 2022/7/2
 * Description :
 */
@Configuration
public class MainConfiguration2 {

    /**
     * 注解@Scope
     *    |- value String （alias scopeName）
     *    |- scopeName String （alias value）
     *      可取值：
     *        ConfigurableBeanFactory#SCOPE_PROTOTYPE 多实例 【prototype】
     *        ConfigurableBeanFactory#SCOPE_SINGLETON 单实例（默认） 【singleton】
     *        org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST web环境下取值,同一次请求创建一个实例 【request】
     *        org.springframework.web.context.WebApplicationContext#SCOPE_SESSION web环境下取值，同一个session创建一个实例  【session】
     *
     *     |- proxyMode ScopedProxyMode
     *
     */
    @Scope("singleton")
    @Bean("ww")
    //默认的是单实例
    public Person getPerson() {
        /**
         * 单实例-singleton： 创建ApplicationContext后就创建了,即创建好IOC时会调用配置类方法创建Bean放到IOC容器中
         *                   以后每次获取都是从IOC容器中拿
         * 多实例-prototype： 在获取对象时才创建，即调用getBean()方法时才会实际创建
         *                   以后每次获取都是重新调用方法，创建新的Bean对象
         */
        System.out.println("给容器中添加Person");
        return new Person("王五",25);
    }

}

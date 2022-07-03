package com.ly.config;

import com.ly.bean.Car;
import com.ly.bean.Cat;
import com.ly.bean.Dog;
import com.ly.bean.MyBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * FileName：MainConfigLifeCycle.java
 * Author：Ly
 * Date：2022/7/3
 * Description： 研究Bean的生命周期
 */

/**
 * 1、Bean的生命周期：
 *     bean创建--初始化---销毁的过程
 *
 * 2、Bean是由IOC容器创建的，但是我们可以自定义bean的初始化和销毁方法
 * 3、生命过程
 *       * 构造（创建）
 *          * 单实例：在容器启动的时候创建对象
 *          * 多实例：在每次获取的时候创建对象
 *
 *       * 初始化
 *          * 单实例：对象创建后，并赋好值，调用初始化方法...
 *          * 多实例：在调用getBean方法时，对象才创建，并赋好值，调用初始化方法...
 *
 *       * 销毁
 *          * 单实例：在IOC容器关闭时销毁
 *          * 多实例：IOC容器不会管理Bean，容器关闭时不会自动调用销毁方法。所以你只能手动调用destroy方法
 * 4、bean的初始化和销毁方法
 *    1).指定初始化和销毁方法
 *         + xml配置中有init-method 和destroy-method
 *         + xml配置中有init-method 和destroy-method
 *    2).让Bean实现InitializingBean接口，Bean对象创建时会自动调用其afterPropertiesSet也就是销毁方法
*        让Bean实现DisposableBean接口，IOC容器销毁时会自动调用其destroy方法
 *        多实例单实例区别同 1)
 *    3).使用JSR250规范中注解：(jdk1.8之后没有了，需要添加依赖)
 *          注解 @PostConstruct：在bean创建完成并且属性赋值完成后，执行被此注解标注的初始化方法
 *          注解 @PreDestroy：当Bean从IOC容器中移除时，调用被此注解标注的销毁方法
 *    4).BeanPostProcessor:bean后置处理器
 *          postProcessBeforeInitialization方法：在所有bean初始化之前进行进行处理
 *          postProcessAfterInitialization方法：在所有bean初始化之后进行进行处理
 */
@ComponentScan(value = "com.ly.bean",includeFilters = {@ComponentScan.Filter(value = {Component.class})})
@Configuration
public class MainConfigLifeCycle {

    @Bean(value = "car",initMethod = "init",destroyMethod = "destroy")
    public Car getCar() {
        return new Car();
    }

    @Bean(value = "cat")
    public Cat getCat() {
        return new Cat();
    }

    @Bean(value = "dog")
    public Dog getDog() {
        return new Dog();
    }




}

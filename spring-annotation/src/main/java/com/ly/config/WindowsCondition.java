package com.ly.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * FileName：MyCondition.java
 * Author：Ly
 * Date：2022/7/2
 * Description： 判断操作系统是不是windows，作为@conditional条件
 */
public class WindowsCondition implements Condition {

    /**
     *
     * @param context 判断条件能使用的上下文环境
     * @param metadata 获取当前注解信息
     * @return 是否满足条件，true 满足
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 1、context.getBeanFactory() 获取IOC容器使用的beanFactory，用来创建bean的
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        // 2、context.getClassLoader() 获取当前类加载器
        ClassLoader classLoader = context.getClassLoader();

        // 3、context.getRegistry() 获取bean定义的注册类，所有的bean都在这里注册
        BeanDefinitionRegistry registry = context.getRegistry();

        // 4、context.getEnvironment() 获取当前环境信息，包括环境变量，系统信息
        String os = context.getEnvironment().getProperty("os.name");
        if (os!=null&&os.contains("Windows")) {
            return true;
        }
        return false;
    }
}

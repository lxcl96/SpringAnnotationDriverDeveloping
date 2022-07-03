package com.ly.config;

import com.ly.bean.Color;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * FileName：MyImportBeanDefinitionRegistrar.java
 * Author：Ly
 * Date：2022/7/2
 * Description：
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 使用内部方法registerBeanDefinitions，自己使用BeanDefinitionRegistry给容器添加/注册组件(不是IOC调用注册机注册)
     * @param importingClassMetadata 当前类所有注解信息（仅限此类，不包括内部方法）
     * @param registry IOC容器bean注册机（所有bean注册均在此注册）
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //先判断当前IOC容器中是否已经存在Color类，如果存在就跳过；如果不存在，在注册
               //根据名字来的 com.ly.bean.Color != color
        if (registry.containsBeanDefinition("color")) {
            System.out.println("当前IOC中，已经有名字为color的组件，跳过注册。");
            return;
        }
        System.out.println("当前IOC中，没有名字为color的组件，注册！");
        registry.registerBeanDefinition("color",new RootBeanDefinition(Color.class));
    }
}

package com.ly.ext;

import com.ly.bean.Color;
import com.ly.bean.Flour;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * FileName:MyBeanDefinitionRegistryPostProcessor.class
 * Author:ly
 * Date:2022/7/13
 * Description: BeanDefinitionRegistryPostProcessor 在 BeanFactoryPostProcessor 前执行
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        int count = registry.getBeanDefinitionCount();
        String[] names = registry.getBeanDefinitionNames();
        System.out.println("所有bean定义将被加载到IOC容器中的个数：" + count + " ,名字：" + Arrays.asList(names));

        //自己创建bean定义信息，IOC容器会根据这个定义信息创建Bean 方法1
        registry.registerBeanDefinition("myFlour", new RootBeanDefinition(Flour.class));

        registry.registerBeanDefinition("myColor", BeanDefinitionBuilder.rootBeanDefinition(Color.class).getBeanDefinition());
        System.out.println("-- BeanDefinitionRegistryPostProcessor，所有BeanFactoryPostProcessor前执行");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("****************************");
    }
}

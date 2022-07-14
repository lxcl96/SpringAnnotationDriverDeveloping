package com.ly.ext;

import com.ly.bean.Cat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * FileName:MyBeanFactoryPostProcessor.class
 * Author:ly
 * Date:2022/7/13
 * Description:
 */

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //注意函数名，获取的是bean定义信息，如果是getbean 那么就会先创建/实例化 cat对象
        Cat cat = beanFactory.getBean("com.ly.bean.Cat", Cat.class);
        System.out.println(cat);

        int count = beanFactory.getBeanDefinitionCount();
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("MyBeanFactoryPostProcessor 结束！ 共定义bean的个数：" + count);

    }
}

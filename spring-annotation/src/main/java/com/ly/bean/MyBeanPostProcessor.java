package com.ly.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * FileName：MyBeanPostProcessor.java
 * Author：Ly
 * Date：2022/7/3
 * Description： 前置/后置处理器
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     *  在bean初始化之前进行进行处理
     * @param bean 就是我们创建的bean新实例
     * @param beanName 就是我们创建的bean新实例的名字
     * @return 要使用的bean实例（可以是原来的，也可以是你自己包装好的）;如果为null，则不会调用后续的BeanPostProcessors函数
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("前置处理器 -- --- 新实例bean = " + bean + " ,名字为：" + beanName);
        return bean;
    }


    /**
     *  在bean初始化之前进行进行处理
     * @param bean 就是我们创建的bean新实例
     * @param beanName 就是我们创建的bean新实例的名字
     * @return 要使用的bean实例（可以是原来的，也可以是你自己包装好的）;如果为null，则不会调用后续的BeanPostProcessors函数
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("后置处理器 -- --- 新实例bean = " + bean + " ,名字为：" + beanName);
        return bean;
    }
}

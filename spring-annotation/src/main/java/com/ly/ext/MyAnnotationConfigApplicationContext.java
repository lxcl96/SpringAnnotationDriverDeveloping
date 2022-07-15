package com.ly.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * FileName:MyAnnotationConfigApplicationContext.class
 * Author:ly
 * Date:2022/7/14
 * Description: 自定义注解启动
 */
public class MyAnnotationConfigApplicationContext extends AnnotationConfigApplicationContext {

    @Override
    protected void initPropertySources() {
        super.initPropertySources();
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);
    }

    @Override
    protected void onRefresh() throws BeansException {
        super.onRefresh();
    }

}

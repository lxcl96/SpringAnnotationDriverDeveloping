package com.ly.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * FileName:Tiger.class
 * Author:ly
 * Date:2022/7/6
 * Description:
 */

public class Tiger implements ApplicationContextAware {
    private ApplicationContext context;

    public Tiger(ApplicationContext context) {
        System.out.println("获取到IOC容器：" + context);
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "Tiger{" +
                "context=" + context +
                '}';
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

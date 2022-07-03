package com.ly.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * FileName：ColorFactory.java
 * Author：Ly
 * Date：2022/7/3
 * Description：  BeanFactory
 */


//创建一个Spring定义的FactoryBean
public class ColorFactoryBean implements FactoryBean<Color> {

    /**
     * 获取一个Bean （此类可以任意）,然后会注册到IOC容器中
     * @return 返回我们需要的类
     * @throws Exception 异常
     */
    @Override
    public Color getObject() throws Exception {
        System.out.println("创建color bean");
        //可以写一些自己需要定义的逻辑
        return new Color();
    }

    /**
     * 获取一个Bean的Class类型
     * @return 返回我们需要的类的Class类型
     * @throws Exception 异常
     */
    @Override
    public Class<?> getObjectType() {
        //可以写一些自己需要定义的逻辑
        return Color.class;
    }

    /**
     * 是否是单实例
     * @return true 单实例；false 多实例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}

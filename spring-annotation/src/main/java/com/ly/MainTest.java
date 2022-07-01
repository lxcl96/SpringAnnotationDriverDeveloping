package com.ly;

import com.ly.bean.Person;
import com.ly.config.MainConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FileName:MainTest.class
 * Author:ly
 * Date:2022/7/1
 * Description: Spring配置文件xml使用和注解使用
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springConfig/beans.xml");
        Person zs = applicationContext.getBean("person", Person.class);
        System.out.println(zs);


        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
        Person ls = (Person) context.getBean("ls");
        System.out.println(ls);

        //获取IOC容器中所有已经注册好的类
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}

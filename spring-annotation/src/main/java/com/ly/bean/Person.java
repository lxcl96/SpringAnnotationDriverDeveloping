package com.ly.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * FileName:Person.class
 * Author:ly
 * Date:2022/7/1
 * Description:
 */
public class Person {

    /**
     * 使用@Value赋值：
     *    1、基本数值
     *    2、可以写SpringEL即Spring表达式 #{}
     *    3、使用${}取出配置文件中[properties文件]的值（也就是IOC容器中环境Environment变量的值）
     *    4、
     */
    @Value("张三")
    private String name;
    @Value("#{20-2}")
    private Integer age;
    @Value("${person.rickName}")
    private String rickName;
    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getRickName() {
        return rickName;
    }

    public void setRickName(String rickName) {
        this.rickName = rickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", rickName='" + rickName + '\'' +
                '}';
    }
}

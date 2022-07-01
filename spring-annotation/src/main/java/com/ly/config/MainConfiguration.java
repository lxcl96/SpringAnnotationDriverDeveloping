package com.ly.config;

import com.ly.bean.Person;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;

/**
 * FileName:MainConfiguration.class
 * Author:ly
 * Date:2022/7/1
 * Description: 配置类代替xml配置文件
 */

//@SuppressWarnings("{all}")
/**
 * 注解 @ComponentScan
 *       |- value  String[] 要扫描的包路径
 *       |- basePackages String[] 要扫描的包路径
 *       |- basePackageClasses class<?>[] 要扫描的类的class类型
 *       |- nameGenerator Class<? extends BeanNameGenerator>
 *       |- scopeResolver Class<? extends ScopeMetadataResolver>
 *       |- scopedProxy ScopedProxyMode
 *       |- resourcePattern String 要扫描的类的class类型
 *       |- useDefaultFilters true 使用默认的过滤器
 *       |- includeFilters Filter[] 包含过滤器，只扫描（只包含）
 *       |- excludeFilters Filter[] 排除过滤器，不扫描（不包含）
 *       |- lazyInit false 不开启懒加载初始化
 *
 *  注：ComponentScan.Filter为注解@ComponentScan下的子注解@Filter
 *
 *
 *  注解 @ComponentScan.Filter
 *       |- type  FilterType 过滤规则【注解排除，类排除。正则表达式排除等等】默认是注解排除
 *       |- value Class<?>[] 过滤规则class 【注解排除的class，类排除的class。正则表达式排除等等】
 *       |- classes Class<?>[] 过滤规则class 【注解排除的class，类排除的class。正则表达式排除等等】
 *       |- pattern String[] 正则表达式
 */

@ComponentScan(basePackages = {"com.ly"},excludeFilters = {
        @ComponentScan.Filter({Controller.class})  //不扫描@Controller注解
},
        useDefaultFilters = false,//只包含扫描，必须关闭默认扫描才会生效
        includeFilters = {
        @ComponentScan.Filter({Service.class}) //只扫描@Service注解
})
@Configuration //告诉Spring这是一个配置类，及配置文件
public class MainConfiguration {

    @Bean("ls")
    public Person getPerson() {
        return new Person("李四",18);
    }
}

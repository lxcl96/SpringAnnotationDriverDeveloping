# @ComponentScan 注解

```java
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
```

## 1、@ComponentScan.Filter 

该注解是 `@ComponentScan` 注解内的 子注解

> 也可以理解为注解类下的子注解类

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Filter {
    FilterType type() default FilterType.ANNOTATION;

    @AliasFor("classes")
    Class<?>[] value() default {};

    @AliasFor("value")
    Class<?>[] classes() default {};

    String[] pattern() default {};
}
```

***用法：***

```java
package com.ly.config;

import com.ly.dao.BookDao;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;


/**
 * FileName:FilterTypeConfiguration.class
 * Author:ly
 * Date:2022/7/1
 * Description:
 */
@SuppressWarnings({"all"})
@ComponentScan(
        value = {"com.ly"},
        useDefaultFilters = false,
        includeFilters = {
            @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class}), //按照 注解类型
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {BookDao.class}), //按照 给定的类class
            @ComponentScan.Filter(type = FilterType.ASPECTJ,value = {}), //按照 ASPECTJ表达式，基本上不用
            @ComponentScan.Filter(type = FilterType.REGEX,value = {}), //按照 正则表达式

            //自定义规则最重要，必须要是org.springframework.core.type.filter.TypeFilter的实现类
                // (自己写的MyFilterType类也会被扫描进行判断，虽然其类上没有任何注解)
            @ComponentScan.Filter(type = FilterType.CUSTOM,value = {MyFilterType.class}) //按照 自定义规则，
        }
)
@Configuration
public class FilterTypeConfiguration {
}
```

***FilterType.CUSTOM自定义过滤规则：***

```java
package com.ly.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * FileName:MyFilterType.class
 * Author:ly
 * Date:2022/7/1
 * Description: FilterType.CUSTOM自定义过滤规则，必须实现TypeFilter接口
 *              且不能为匿名内部类，因为必须为常量
 */
public class MyFilterType implements TypeFilter {

    /**
     *
     * @param metadataReader 读取到IOC容器当前正在扫描的类的信息
     * @param metadataReaderFactory 可以获取到其他任何类信息的工厂
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前IOC正在扫描的类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前IOC正在扫描类的类信息（如类型，实现的接口啊,类名，父类名，子类名）
        ClassMetadata metadata = metadataReader.getClassMetadata();
        String[] interfaceNames = metadata.getInterfaceNames();
        String[] memberClassNames = metadata.getMemberClassNames();
        String superClassName = metadata.getSuperClassName();
        String className = metadata.getClassName();
        System.out.println("当前类名" + className);
        System.out.println(className + "的父类们名：" + superClassName);
        System.out.println(className + "的子类们名：" + Arrays.toString(memberClassNames));
        System.out.println(className + "的接口们名：" + Arrays.toString(interfaceNames));

        //获取当前IOC容器正在扫描的类资源（如类的路径）
        Resource resource = metadataReader.getResource();

        //如果扫描的包com.ly下有类名包含service的,就注册到IOC容器中(不区分大小写)
        if (className.contains("service")){
            return true;
        }
        //返回false说明一个都匹配不上，都不注册到IOC容器中
        return false;
    }
}
```

******





# @ComponentScans 注解

​	因为在Jdk1.8下``@ComponentScan`可以重复写

```java
@ComponentScan("com.ly.bean")
@ComponentScan("com.ly.controller")
@Configuration
class MyConfiguration {...}
```

​	如果Jdk1.8以下的的呢？怎么写多个？就需要使用注解`@ComponentScans `

```java
/*
@ComponentScans里面是 @ComponentScan[]
*/
@ComponentScans({
    @ComponentScan("com.ly.bean"),
    @ComponentScan("com.ly.controller")
})
@Configuration
class MyConfiguration {...}
```


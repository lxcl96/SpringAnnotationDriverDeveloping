package com.ly.config;

import com.ly.bean.Person;
import org.springframework.context.annotation.*;

/**
 * FileName:MainConfigOfPropertyValues.class
 * Author:ly
 * Date:2022/7/4
 * Description: @Value注解
 */

/**
 * 注解@PropertySource
 *     等价于xml中引入外部文件： <context:property-placeholder location="classpath:person.properties" />
 *    参数：
 *      name:名称，没啥用。IOC容器中也不回有这个名字的对象，取值还是直接properties文件中的key ${key}
 *      value:配置文件数据classpath类路径 或file文件路径
 *      encoding：配置文件编码
 *      ignoreResourceNotFound：没找到配置文件是否忽略？默认false，会报错
 *      factory：待定
 */
@PropertySource(name = "personProperty",value = {"classpath:person.properties"},encoding = "utf-8")
@ComponentScan
@Configuration
public class MainConfigOfPropertyValues {

    @Bean("onePerson")
    public Person getPerson(){
        return new Person();
    }
}

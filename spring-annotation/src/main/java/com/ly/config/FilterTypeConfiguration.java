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

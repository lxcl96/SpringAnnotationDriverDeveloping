package com.ly.config;

import com.ly.bean.Tiger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName:IOCGetConfiguration.class
 * Author:ly
 * Date:2022/7/6
 * Description:
 */
@Configuration
public class IOCGetConfiguration {
    @Bean("tiger")
    public Tiger getOne(ApplicationContext context) {
        return new Tiger(context);
    }
}

package com.ly.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

/**
 * FileName:RootConfig.class
 * Author:ly
 * Date:2022/7/21
 * Description: 根容器配置文件（即Spring配置类）
 *
 * 只不扫描Controller组件，和SpringMVC配形成互补
 */
@ComponentScan(value = {"com.ly"},excludeFilters = {
        @ComponentScan.Filter({Controller.class})
})
public class RootConfig {
}

package com.ly;

import com.ly.config.AppConfig;
import com.ly.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * FileName:MyWebAppInitializer.class
 * Author:ly
 * Date:2022/7/21
 * Description: 注解方式实现SpringMVC使用，代替web.xml,
 *      应用启动时会自动扫描每个jar包下META-INF\services\javax.servlet.ServletContainerInitializer文件
 *      因为此类时AbstractAnnotationConfigDispatcherServletInitializer的子类，所以也就是WebApplicationInitializer的子类，所以我们不需要手动添加到META-INF下文件中，会自动扫描
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 这里配置父容器，即返回Spring的配置类
     * @return Spring配置类们
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 这里配置子容器，即返回SpringMVC的配置类
     * @return SpringMVC配置类们
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    /**
     * 配置DispatcherServlet的拦截路径
     * @return 拦截路径
     */
    @Override
    protected String[] getServletMappings() {
        //拦截所有资源，除了jsp的
        // /*所有的都拦截，包括jsp页面  【jsp页面为tomcat的jsp引擎解析的，不是dispatcherServlet】
        return new String[]{"/"};
    }
}

package com.ly.config;


import com.ly.controller.MyInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * FileName:AppConfig.class
 * Author:ly
 * Date:2022/7/21
 * Description:子容器配置文件（即SpringMVC）
 *
 *  只扫描Controller组件(必须useDefaultFilters = false，默认全扫描)，和Spring配形成互补
 *
 *  配置SpringMVC通过继承 WebMvcConfigurerAdapter
 *  1、@EnableWebMvc    == <mvc:annotation-driven/>
 *  2、configureDefaultServletHandling    ==  <mvc:default-servlet-handler/>
 *  3、configureViewResolvers      == 视图解析器
 *  4、addInterceptors    == 拦截器
 *  5、
 *  6、
 *  7、
 */
@Configuration
@EnableWebMvc //等同于xml中 <mvc:annotation-driven/>
@ComponentScan(value = {"com.ly"},includeFilters = {
        @ComponentScan.Filter({Controller.class})
},useDefaultFilters = false)
//通过继承WebMvcConfigurerAdapter类，重新方法完成SpringMVC配置
public class AppConfig extends WebMvcConfigurerAdapter {

    public AppConfig() {
        super();
    }

    @Override
    //配置路径映射规则
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    //配置默认的servlet处理器，开启静态资源 [给tomcat处理，因为dispatcherServlet默认拦截所有无法处理静态资源]
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
        configurer.enable();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new MyInterceptor());
        //   /**表示；拦截所有请求，/*表示拦截单层所有请求 即只能匹配/xxx 无法匹配/xxx/xx等
        interceptorRegistration.addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }

    @Override
    //视图解析器
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setOrder(1);
        viewResolver.setPrefix("/WEB-INF/views/"); //等同于registry.jsp()
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    public Validator getValidator() {
        return super.getValidator();
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return super.getMessageCodesResolver();
    }
}

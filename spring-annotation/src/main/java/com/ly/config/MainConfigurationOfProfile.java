package com.ly.config;

/**
 * FileName:MainConfigurationOfProfile.class
 * Author:ly
 * Date:2022/7/6
 * Description:Profile使用，以数据源为例
 */


import com.ly.bean.Flour;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;
import java.beans.PropertyVetoException;
import javax.sql.DataSource;
/**
 *  注解@Profile：
 *      Spring为我们提供的可以根据当前环境，动态的激活和切换一系列bean组件的功能。
 *
 *  应用：如数据源Bean，日志Bean切换（开发环境，测试环境，生产环境）
 *  举例：以数据源为例
 *
 * @Profile:指定组件在哪个环境的情况下才能被注册到容器中，默认不指定，所有组件均注册到IOC容器中
 */
@SuppressWarnings({"all"})
@Configuration
@PropertySource(value = {"classpath:jdbc.properties"})
public class MainConfigurationOfProfile implements EmbeddedValueResolverAware {
    //花式取值
    @Value("${dev.driver}")
    private String driver;
    //spring值解析器，用于解析占位符#{}和${}
    private StringValueResolver valueResolver;


    @Profile("developing")
    @Bean("dev")
    //花式取值
    public DataSource getDevDataSource(@Value("${dev.url}") String url) throws PropertyVetoException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(valueResolver.resolveStringValue("${dev.user}"));
        dataSource.setPassword("${dev.password}");
        return dataSource;
    }

    @Profile("testing")
    @Bean("test")
    public DataSource getTestDataSource() throws PropertyVetoException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("${test.driver}");
        dataSource.setJdbcUrl("${test.url}");
        dataSource.setUser("${test.user}");
        dataSource.setPassword("${test.password}");
        return dataSource;
    }

    @Profile("production")
    @Bean("production")
    public DataSource getProductionDataSource() throws PropertyVetoException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("${production.driver}");
        dataSource.setJdbcUrl("${production.url}");
        dataSource.setUser("${production.user}");
        dataSource.setPassword("${production.password}");
        return dataSource;
    }

    @Profile("default") //不标记，或者标记default的默认都会被注册到IOC容器中。
    // 但是如果切换了环境，则default的bean组件不会被注册到IOC容器中，不写@Profile注解的Bean总是会被注册到IOC容器中
    @Bean("defaultDatasource")
    public DataSource getDefaultDatasource() throws PropertyVetoException {

        return null;
    }
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
    }

    @Bean("flour")
    public Flour getOne() {
        return new Flour();
    }
}

package com.ly.tx;

/**
 * FileName:TxConfig.class
 * Author:ly
 * Date:2022/7/12
 * Description: 事务管理
 */


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务:
 *
 * 环境搭建：
 *   1、导入依赖（数据源，数据库驱动，Spring-jdbc模块）
 *   2、配置数据源和JDBCTemplate（Spring提供简化数据库操作的工具）操作数据
 *   3、Service层方法加上@Transactional注解，开启事务
 *   4、配置类上加入 @EnableTransactionManagement
 *   5、配置事务管理器(IOC容器中事务管理器的名字必须为：transactionManager)进行事务管理
 */

@Configuration
@ComponentScan("com.ly.tx")
@EnableTransactionManagement
@PropertySource(value = {"classpath:jdbc.properties"})
public class TxConfig {
    @Value("${tx.driver}")
    private String driverClass;
    @Value("${tx.url}")
    private String jdbcUrl;
    @Value("${tx.user}")
    private String user;
    @Value("${tx.password}")
    private String pwd;

    /**
     * 获取数据源
     * @return 数据源
     * @throws PropertyVetoException 异常
     */
    @Bean("dataSource")
    public DataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(pwd);

        return dataSource;

    }

    /**
     * 为了方便操作数据库，使用JDBCTemplate
     * @return jdbc模板
     */
    @Bean("jdbcTemplate") //Spring会自动注入参数
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        //Spring会对@Configuration 文件特殊处理：给容器中添加组件的方法，多次调用都只是从容器中找组件
        //template.setDataSource(getDataSource());
        return template;
    }

    /**
     * 配置事务管理器
     * @return 事物管理器
     * @throws PropertyVetoException 异常
     */
    @Bean("transactionManager")
    public PlatformTransactionManager getDataSourceTransactionManager() throws PropertyVetoException {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(getDataSource());
        return transactionManager;
    }
}

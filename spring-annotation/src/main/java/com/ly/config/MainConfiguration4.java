package com.ly.config;

import com.ly.bean.Color;
import com.ly.bean.ColorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * FileName：MainConfiguration4.java
 * Author：Ly
 * Date：2022/7/2
 * Description：
 */


/**
 * 注解 @Import
 *      |- value  Class<?>[]
 *  用法：
 *    1). @Import(Color.class) IOC容器会自动注册这个组件，id默认就是全类名
 *    2). ImportSelector 是一个接口，
 *          selectImports()返回需要导入的组件的全类名数组
 *          getExclusionFilter()排出的类的全类名(只能排除selectImports方法中返回的需要导入的全类名) ,【@Import注解中导入的或@bean注册的都无法排除】
 *              返回true代表排除指定的全类名，
 *              返回false代表不排除
 *    3).ImportBeanDefinitionRegistrar 是一个接口，使用内部方法registerBeanDefinitions，自己使用BeanDefinitionRegistry给容器添加/注册组件(不是IOC调用注册机注册)
 *              registerBeanDefinitions() 手动向IOC中注册bean
 *
 */

@Configuration
//@Import(Color.class)
//@Import({Color.class,MyImportSelector.class})
//@Import({MyImportBeanDefinitionRegistrar.class,Color.class})
public class MainConfiguration4 {

    //FactoryBean通过id直接拿到的就是我们需要的Color类  applicationContext.getBean("color");
    //FactoryBean本身不会放在IOC容器中，但是可以在这样获取
    @Bean("color") //通过此id获取的其实是Color类
    public ColorFactoryBean getFactoryBean() {
        System.out.println("创建一个ColorFactoryBean");
        return new ColorFactoryBean();
    }
}

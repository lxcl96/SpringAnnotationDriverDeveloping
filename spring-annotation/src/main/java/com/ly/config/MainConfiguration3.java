package com.ly.config;

import com.ly.bean.Person;
import org.springframework.context.annotation.*;

/**
 * FileName：MainConfiguration3.java
 * Author：Ly
 * Date：2022/7/2
 * Description：
 */
@Import(MyImportSelector.class)
@Configuration
public class MainConfiguration3 {

    @Lazy(value = true)
    @Bean("zl")
    public Person getPerson(){
        System.out.println("赵六，已经被创建好了！");
        return new Person("赵六",35);
    }

    /**
     * 注解 @Conditional
     *     |- value Class<? extends Condition>[] 实现Condition接口的类class数组
     *
     *     按照一定的条件进行判断，满足条件的给容器中创建Bean
     *
     *  如：判断系统如果是Windows，就注册bill gates；如果是linux，就注册linus
     *
     */
    @Conditional(WindowsCondition.class)
    @Bean("windows")
    public Person getWindows(){
        return new Person("Bill Gates",40);
    }

    @Conditional(LinuxCondition.class)
    @Bean("linux")
    public Person getLinux(){
        return new Person("Linus",45);
    }
}

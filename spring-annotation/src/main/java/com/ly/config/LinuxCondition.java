package com.ly.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * FileName：LinuxCondition.java
 * Author：Ly
 * Date：2022/7/2
 * Description： 判断操作系统是不是linux，作为@conditional条件
 */
public class LinuxCondition implements Condition {

    /**
     *
     * @param context 判断条件能使用的上下文环境
     * @param metadata 获取当前注解信息
     * @return 是否是linux系统，true 满足
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //如果系统中包含了名字为windows这个对象，照样注册linux
        BeanDefinitionRegistry registry = context.getRegistry();
        boolean windows = registry.containsBeanDefinition("windows");
        if (windows) {
            System.out.println("已经包含windows对象，注册linux.");
            return true;
        }
        //
        String os = context.getEnvironment().getProperty("os.name");
        if (os!=null&&os.contains("Linux")) {
                return true;
        }

             return false;
    }
}

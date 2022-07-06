package test;

import com.ly.config.MainConfigurationOfProfile;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * FileName:ProfileAnnotationTest.class
 * Author:ly
 * Date:2022/7/6
 * Description: @Profile注解测试
 */
public class ProfileAnnotationTest {

    /**
     * 切换运行环境到 @Profile("xxx")对应的环境xxx
     * 方法1：启动命令行参数加上：-Dspring.profiles.active=xxx 如：-Dspring.profiles.active=developing
     * 方法2：使用代码的方法，需要使用IOC容器的无参数构造器
     */
    @Test
    public void testProfile(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigurationOfProfile.class);

        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        applicationContext.close();
    }
}

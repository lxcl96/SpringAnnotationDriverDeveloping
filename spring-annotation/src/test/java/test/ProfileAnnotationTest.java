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
        //applicationContext.getEnvironment().setActiveProfiles("developing");
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        applicationContext.close();
    }

    /**
     * 切换运行环境到 @Profile("xxx")对应的环境xxx
     * 方法1：启动命令行参数加上：-Dspring.profiles.active=xxx 如：-Dspring.profiles.active=developing
     * 方法2：使用代码的方法，需要使用IOC容器的无参数构造器
     *       指定profile环境后，后面的代码和有参构造器的完全一样即可
     */
    @Test
    public void testProfileByCode(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //设置profile环境
        applicationContext.getEnvironment().setActiveProfiles("developing","testing");
        //遵循有参构造器的代码
        applicationContext.register(MainConfigurationOfProfile.class);
        applicationContext.refresh();

        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        applicationContext.close();
    }
}

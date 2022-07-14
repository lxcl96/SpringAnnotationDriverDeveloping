package test;

import com.ly.ext.ExtConfig;
import com.ly.ext.eventDeveloping.Config;
import com.ly.ext.eventDeveloping.UserController;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * FileName:SpringExtTest.class
 * Author:ly
 * Date:2022/7/13
 * Description:
 */
public class SpringExtTest {

    @Test
    public void testMyBeanFactoryPostProcessor() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        System.out.println("test测试");
        applicationContext.close();
    }


    @Test
    public void testMyApplicationListener(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        applicationContext.publishEvent(new ApplicationEvent(new String("自己的事件")) {
        });
        System.out.println("test测试");
        applicationContext.close();
    }

    @Test
    public void testEventDeveloping(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        //模拟用户登陆，手动传递IOC容器
        applicationContext.getBean("userController", UserController.class).login(applicationContext);

        applicationContext.close();
    }

}

package test;

import com.ly.aop.MathCalculator;
import com.ly.config.MainConfigurationOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * FileName:AOPTest.class
 * Author:ly
 * Date:2022/7/7
 * Description:
 */
public class AOPTest {

    @Test
    public void testSimpleAOP() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigurationOfAOP.class);

        //正常调用MathCalculator的div方法即可
        //MathCalculator calculator = new MathCalculator();  千万别自己创建，要从IOC容器中取
        MathCalculator calculator = (MathCalculator) applicationContext.getBean("calculator");
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        int ret = calculator.div(2, 0);

        System.out.println(ret);

        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }


}

package test;

import com.ly.bean.*;
import com.ly.config.*;
import com.ly.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * Author : Ly
 * Date : 2022/7/2
 * Description :
 */
public class IOCTest {

    @Test
    public void test() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiguration2.class);
        String[] names = applicationContext.getBeanDefinitionNames();

        /**
         * 1、多实例protoType模式下：
         *       虽然我调用多次getBean()方法，获取了不同的ww1，ww2但是SpringIoc容器中始终只会有一个Bean即 ww（@Bean("ww")确定）。
         *       也就是说虽然我们创建了多个Bean对象，但是IOC容器中始终保存的是最后一个最新的即 ww==ww2
         *
         * 2、单实例singleton模式下：
         *       每次获得都是同一个bean即 ww==ww1==ww2
         *
         * 3、IOC容器中 单/多实例 模式下组件注册的步骤：
         *      单实例-singleton： 创建ApplicationContext后就创建了,即创建好IOC时会调用配置类方法创建Bean放到IOC容器中
         *                        以后每次获取都是从IOC容器中拿
         *      多实例-prototype： 在获取对象时才创建，即调用getBean()方法时才会实际创建
         *                        以后每次获取都是重新调用方法，创建新的Bean对象
         */
        Person ww1 = (Person) applicationContext.getBean("ww");
        Person ww2 = (Person) applicationContext.getBean("ww");
        System.out.println("ww1 == ww2 ? " + (ww1==ww2));

        names = applicationContext.getBeanDefinitionNames();
        //默认的是单实例
        for (String name : names) {
            System.out.println(name);
        }

    }

    @Test
    public void testLazyAnnotation() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiguration3.class);
        //获取IOC容器中所有Person类的定义的组件
        String[] names = applicationContext.getBeanNamesForType(Person.class);

        for (String name : names) {
            System.out.println(name);
        }

        //通过bean类型获取bean组件
        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
        System.out.println(beansOfType);


        //获取系统环境
        String property = applicationContext.getEnvironment().getProperty("os.name");
        System.out.println(property);
    }

    @Test
    public void testImport() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiguration4.class);
        //Color color = (Color) applicationContext.getBean("com.ly.bean.Color");
        //System.out.println(color);//com.ly.bean.Color@1c742ed4


        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testFactoryBean() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiguration4.class);

        //通过id 实际上获取的是ColorFactoryBean.getObject()方法返回的Color
        Color color = (Color) applicationContext.getBean("color");
        System.out.println(color);


        //获取ColorFactoryBean 本身是这样获取，通过类型(FactoryBean本身不会注册到IOC容器中)
        Map<String, ColorFactoryBean> factoryBeanMap = applicationContext.getBeansOfType(ColorFactoryBean.class);
        System.out.println(factoryBeanMap);
        ColorFactoryBean bean = (ColorFactoryBean) applicationContext.getBean("&color");
        System.out.println(bean);

        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testBeanLifeCycle() throws Exception {
        //只有子类才有close方法
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);

        //applicationContext.getBean("dog");
        //手动销毁IOC容器
        applicationContext.close();
    }

    @Test
    public void testPropertyValues() throws Exception {
        //只有子类才有close方法
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);

        //获通过IOC容器的环境变量获取配置文件数据
        String rickName = applicationContext.getEnvironment().getProperty("person.rickName");
        System.out.println(rickName);

        System.out.println(applicationContext.getBean("onePerson"));


        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testAutowire() throws Exception {
        //只有子类才有close方法
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowire.class);

        //获取IOC容器中的car
        Car car = (Car) applicationContext.getBean("car");
        //获取IOC容器中的boss
        Boss boss = (Boss) applicationContext.getBean("boss");
        Color color = (Color) applicationContext.getBean("color");

        System.out.println("boss的car是不是IOC容器的car？" + (boss.getCar()==car));
        System.out.println(color);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testAutowireGetIOC() throws Exception {
        //只有子类才有close方法
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(IOCGetConfiguration.class);

        System.out.println("创建出来的IOC容器：" + applicationContext);


        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}

# 一：IOC容器的组件注册

## 1、@ComponentScan 注解

```java
package com.ly.config;

import com.ly.bean.Person;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;

/**
 * FileName:MainConfiguration.class
 * Author:ly
 * Date:2022/7/1
 * Description: 配置类代替xml配置文件
 */

//@SuppressWarnings("{all}")
/**
 * 注解 @ComponentScan
 *       |- value  String[] 要扫描的包路径
 *       |- basePackages String[] 要扫描的包路径
 *       |- basePackageClasses class<?>[] 要扫描的类的class类型
 *       |- nameGenerator Class<? extends BeanNameGenerator>
 *       |- scopeResolver Class<? extends ScopeMetadataResolver>
 *       |- scopedProxy ScopedProxyMode
 *       |- resourcePattern String 要扫描的类的class类型
 *       |- useDefaultFilters true 使用默认的过滤器
 *       |- includeFilters Filter[] 包含过滤器，只扫描（只包含）
 *       |- excludeFilters Filter[] 排除过滤器，不扫描（不包含）
 *       |- lazyInit false 不开启懒加载初始化
 *
 *  注：ComponentScan.Filter为注解@ComponentScan下的子注解@Filter
 *
 *
 *  注解 @ComponentScan.Filter
 *       |- type  FilterType 过滤规则【注解排除，类排除。正则表达式排除等等】默认是注解排除
 *       |- value Class<?>[] 过滤规则class 【注解排除的class，类排除的class。正则表达式排除等等】
 *       |- classes Class<?>[] 过滤规则class 【注解排除的class，类排除的class。正则表达式排除等等】
 *       |- pattern String[] 正则表达式
 */

@ComponentScan(basePackages = {"com.ly"},excludeFilters = {
        @ComponentScan.Filter({Controller.class})  //不扫描@Controller注解
},
        useDefaultFilters = false,//只包含扫描，必须关闭默认扫描才会生效
        includeFilters = {
        @ComponentScan.Filter({Service.class}) //只扫描@Service注解
})
@Configuration //告诉Spring这是一个配置类，及配置文件
public class MainConfiguration {

    @Bean("ls")
    public Person getPerson() {
        return new Person("李四",18);
    }
}
```

## @ComponentScan.Filter 

该注解是 `@ComponentScan` 注解内的 子注解，用于配置包扫描过滤。（只包含，不包含等）

> 也可以理解为注解类下的子注解类

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Filter {
    FilterType type() default FilterType.ANNOTATION;

    @AliasFor("classes")
    Class<?>[] value() default {};

    @AliasFor("value")
    Class<?>[] classes() default {};

    String[] pattern() default {};
}
```

***用法：***

```java
package com.ly.config;

import com.ly.dao.BookDao;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;


/**
 * FileName:FilterTypeConfiguration.class
 * Author:ly
 * Date:2022/7/1
 * Description:
 */
@SuppressWarnings({"all"})
@ComponentScan(
        value = {"com.ly"},
        useDefaultFilters = false,
        includeFilters = {
            @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class}), //按照 注解类型
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {BookDao.class}), //按照 给定的类class
            @ComponentScan.Filter(type = FilterType.ASPECTJ,value = {}), //按照 ASPECTJ表达式，基本上不用
            @ComponentScan.Filter(type = FilterType.REGEX,value = {}), //按照 正则表达式

            //自定义规则最重要，必须要是org.springframework.core.type.filter.TypeFilter的实现类
                // (自己写的MyFilterType类也会被扫描进行判断，虽然其类上没有任何注解)
            @ComponentScan.Filter(type = FilterType.CUSTOM,value = {MyFilterType.class}) //按照 自定义规则，
        }
)
@Configuration
public class FilterTypeConfiguration {
}
```

***FilterType.CUSTOM自定义过滤规则：***

```java
package com.ly.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * FileName:MyFilterType.class
 * Author:ly
 * Date:2022/7/1
 * Description: FilterType.CUSTOM自定义过滤规则，必须实现TypeFilter接口
 *              且不能为匿名内部类，因为必须为常量
 */
public class MyFilterType implements TypeFilter {

    /**
     *
     * @param metadataReader 读取到IOC容器当前正在扫描的类的信息
     * @param metadataReaderFactory 可以获取到其他任何类信息的工厂
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前IOC正在扫描的类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前IOC正在扫描类的类信息（如类型，实现的接口啊,类名，父类名，子类名）
        ClassMetadata metadata = metadataReader.getClassMetadata();
        String[] interfaceNames = metadata.getInterfaceNames();
        String[] memberClassNames = metadata.getMemberClassNames();
        String superClassName = metadata.getSuperClassName();
        String className = metadata.getClassName();
        System.out.println("当前类名" + className);
        System.out.println(className + "的父类们名：" + superClassName);
        System.out.println(className + "的子类们名：" + Arrays.toString(memberClassNames));
        System.out.println(className + "的接口们名：" + Arrays.toString(interfaceNames));

        //获取当前IOC容器正在扫描的类资源（如类的路径）
        Resource resource = metadataReader.getResource();

        //如果扫描的包com.ly下有类名包含service的,就注册到IOC容器中(不区分大小写)
        if (className.contains("service")){
            return true;
        }
        //返回false说明一个都匹配不上，都不注册到IOC容器中
        return false;
    }
}
```

******

## 2、@ComponentScans 注解

​	因为在Jdk1.8下``@ComponentScan`可以重复写

```java
@ComponentScan("com.ly.bean")
@ComponentScan("com.ly.controller")
@Configuration
class MyConfiguration {...}
```

​	如果Jdk1.8以下的的呢？怎么写多个？就需要使用注解`@ComponentScans `

```java
/*
@ComponentScans里面是 @ComponentScan[]
*/
@ComponentScans({
    @ComponentScan("com.ly.bean"),
    @ComponentScan("com.ly.controller")
})
@Configuration
class MyConfiguration {...}
```



## 3、@Scope 

用于配置单实例或多实例

配置类：MainConfiguration2.java

```java
package com.ly.config;

import com.ly.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Author : Ly
 * Date : 2022/7/2
 * Description :
 */
@Configuration
public class MainConfiguration2 {

    /**
     * 注解@Scope
     *    |- value String （alias scopeName）
     *    |- scopeName String （alias value）
     *      可取值：
     *        ConfigurableBeanFactory#SCOPE_PROTOTYPE 多实例 【prototype】
     *        ConfigurableBeanFactory#SCOPE_SINGLETON 单实例（默认） 【singleton】
     *        org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST web环境下取值,同一次请求创建一个实例 【request】
     *        org.springframework.web.context.WebApplicationContext#SCOPE_SESSION web环境下取值，同一个session创建一个实例  【session】
     *
     *     |- proxyMode ScopedProxyMode
     *
     */
    @Scope("singleton")
    @Bean("ww")
    //默认的是单实例
    public Person getPerson() {
        /**
         * 单实例-singleton： 创建ApplicationContext后就创建了,即创建好IOC时会调用配置类方法创建Bean放到IOC容器中
         *                   以后每次获取都是从IOC容器中拿
         * 多实例-prototype： 在获取对象时才创建，即调用getBean()方法时才会实际创建
         *                   以后每次获取都是重新调用方法，创建新的Bean对象
         */
        System.out.println("给容器中添加Person");
        return new Person("王五",25);
    }

}
```

测试方法：

```java
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
```

## 4、@Lazy

==配置类里，xml配置文件等IOC启动时扫描到了就把组件（bean）注册到IOC容器中了如：zs，ls，ww，zl等，但是IOC中是有这个类型并没有创建这个对象。可以理解为指向空指针。==

`@Lazy`懒加载是针对单实例`@scope("singleton")`而言的。

因为单实例下bean，是在IOC容器启动时创建的（`new AnnotationConfigApplicationContext()... `），然后创建放到IOC容器中的。

`@Lazy`懒加载就是让单实例下bean和多实例下bean一样，只在调用`getBean()`方法时创建对象。

```java
@Lazy(value = true) //默认为true，也可以不写
@Bean("zl")
public Person getPerson(){
    System.out.println("赵六，已经被创建好了！");
    return new Person("赵六",35);
}
```

## 5、@Conditional

==此注解可以用于类或方法上==

​	按照一定的条件进行判断，满足条件的给容器中创建Bean

```java
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

//@Conditional(LinuxCondition.class)
@Bean("linux")
public Person getLinux(){
    return new Person("Linus",45);
}
```

> 注：可以在Idea运行配置加上vm变量 -Dos.name=Linux，来修改操作系
>
> ![image-20220702172317955](.\img\image-20220702172317955.png)

***条件1：windows判断***

```java
package com.ly.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * FileName：MyCondition.java
 * Author：Ly
 * Date：2022/7/2
 * Description： 判断操作系统是不是windows，作为@conditional条件
 */
public class WindowsCondition implements Condition {

    /**
     *
     * @param context 判断条件能使用的上下文环境
     * @param metadata 获取当前注解信息
     * @return 是否满足条件，true 满足
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 1、context.getBeanFactory() 获取IOC容器使用的beanFactory，用来创建bean的
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        // 2、context.getClassLoader() 获取当前类加载器
        ClassLoader classLoader = context.getClassLoader();

        // 3、context.getRegistry() 获取bean定义的注册类，所有的bean都在这里注册
        BeanDefinitionRegistry registry = context.getRegistry();

        // 4、context.getEnvironment() 获取当前环境信息，包括环境变量，系统信息
        String os = context.getEnvironment().getProperty("os.name");
        if (os!=null&&os.contains("Windows")) {
            return true;
        }
        return false;
    }
}
```

***条件2：linux判断***

```java
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

```



***获取IOC环境Environment：***

```java
applicationContext.getEnvironment().getProperty("os.name");
//一共可以获取57中 系统环境信息
```

![image-20220702165448268](.\img\image-20220702165448268.png)

![image-20220702165549788](.\img\image-20220702165549788.png)

## 6、@import

快速给容器中导入一个组件

### 用法1：直接导入类

```java
/**
 * 注解 @Import
 *      |- value  Class<?>[]
 *  用法：
 *    1). @Import(Color.class) IOC容器会自动注册这个组件，id默认就是全类名
 *  
 */

@Configuration
//@Import(Color.class)
public class MainConfiguration4 {
}
```

### 用法2：根据ImportSelector导入

***自定义导入选择器：MyImportSelector***

```java
package com.ly.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Set;
import java.util.function.Predicate;

/**
 * FileName：MyImportSelector.java
 * Author：Ly
 * Date：2022/7/2
 * Description： 自定义逻辑，返回需要的组件
 */
public class MyImportSelector implements ImportSelector {
    /**
     * 需要导入组件的 字String[]
     * @param importingClassMetadata 当前标注@Import类的所有注解信息（如：MainConfiguration4.java类上的所有注解信息，到不到里面方法）
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //获取 MainConfiguration4.java类上的注解类（Import和Configuration）
        Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
        //获取 MainConfiguration4.java类上的所有注解
        MergedAnnotations annotations = importingClassMetadata.getAnnotations();
        // 获取 MainConfiguration4.java类上 注解名为 Bean的全类名的注解，返回null 因为没有
        MultiValueMap<String, Object> allAnnotationAttributes = importingClassMetadata.getAllAnnotationAttributes("org.springframework.context.annotation.Bean");

        /**
         * 可以写一些条件，最后返回要导入的类全类名
         */
        return new String[]{"com.ly.bean.Color","com.ly.bean.Person"};
    }

   /**
     * 只能排除selectImports方法中返回的需要导入的全类名 【@Import注解中导入的或@bean注册的都无法排除】
     * @return 返回true代表排除指定的全类名，返回false代表不排除
     */
    @Override
    public Predicate<String> getExclusionFilter() {
        return new Predicate<String>() {
            @Override
            public boolean test(String s) {
                if ("com.ly.bean.Person".equals(s)) {
                    return true;
                }
                return false;
            }
        };
    }
}

```

***配置类使用导入选择器：MainConfiguration4.java***

```java
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
 *    3).
 */

@Configuration
//@Import(Color.class)
@Import({Color.class,MyImportSelector.class})
public class MainConfiguration4 {

}
```

***测试使用：***

```java
@Test
public void testImport() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiguration4.class);
    Color color = (Color) applicationContext.getBean("com.ly.bean.Color");
    System.out.println(color);//com.ly.bean.Color@1c742ed4

    }
}
```
### 用法3：ImportBeanDefinitionRegistrar

使用该`ImportBeanDefinitionRegistrar`接口内部方法`registerBeanDefinitions`，自己使用`BeanDefinitionRegistry`给容器添加/注册组件(不是IOC调用注册机注册)

***手动注册bean到IOC容器中：必须实现ImportBeanDefinitionRegistrar***

```java
package com.ly.config;

import com.ly.bean.Color;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * FileName：MyImportBeanDefinitionRegistrar.java
 * Author：Ly
 * Date：2022/7/2
 * Description：
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 使用内部方法registerBeanDefinitions，自己使用BeanDefinitionRegistry给容器添加/注册组件(不是IOC调用注册机注册)
     * @param importingClassMetadata 当前类所有注解信息（仅限此类，不包括内部方法）
     * @param registry IOC容器bean注册机（所有bean注册均在此注册）
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //先判断当前IOC容器中是否已经存在Color类，如果存在就跳过；如果不存在，在注册
               //根据名字来的 com.ly.bean.Color != color
        if (registry.containsBeanDefinition("color")) {
            System.out.println("当前IOC中，已经有名字为color的组件，跳过注册。");
            return;
        }
        System.out.println("当前IOC中，没有名字为color的组件，注册！");
        registry.registerBeanDefinition("color",new RootBeanDefinition(Color.class));
    }
}
```

***@Import导入自定义注册类：***

```java
package com.ly.config;

import com.ly.bean.Color;
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
 */

@Configuration
//@Import(Color.class)
//@Import({Color.class,MyImportSelector.class})
@Import({MyImportBeanDefinitionRegistrar.class,Color.class})
public class MainConfiguration4 {

}
```



## 7、@FactoryBean

有时候创建一个类A需要依赖创建很多其他的类，但是我们只需要A类，不需要知道其创建过程时就选择`@FactoryBean`即工厂Bean。（也就是创建的Bean和返回的 Bean不是同一个类型的。）

==FactoryBean本身不会注册到IOC容器中，但是可以根据类型取到。==

==通过@Bean 的id获取到的实际是Bean 而不是FactoryBean==

`获取FactoryBean本身：`

+ `applicationContext.getBean("&color")`
+ `applicationContext.getBeansOfType(ColorFactoryBean.class)`

`获取Bean：`

+ `applicationContext.getBean("color");`

***创建FactoryBean：***

```java
package com.ly.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * FileName：ColorFactory.java
 * Author：Ly
 * Date：2022/7/3
 * Description：  BeanFactory
 */


//创建一个Spring定义的FactoryBean
public class ColorFactoryBean implements FactoryBean<Color> {

    /**
     * 获取一个Bean （此类可以任意）,然后会注册到IOC容器中
     * @return 返回我们需要的类
     * @throws Exception 异常
     */
    @Override
    public Color getObject() throws Exception {
        System.out.println("创建color bean");
        //可以写一些自己需要定义的逻辑
        return new Color();
    }

    /**
     * 获取一个Bean的Class类型
     * @return 返回我们需要的类的Class类型
     * @throws Exception 异常
     */
    @Override
    public Class<?> getObjectType() {
        //可以写一些自己需要定义的逻辑
        return Color.class;
    }

    /**
     * 是否是单实例
     * @return true 单实例；false 多实例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
```

***配置类中调用ColorFactoryBean：***

==注意：通过@Bean方式，虽然返回类型写的是`ColorFactoryBean`但是实际就是Color==

即：返回类型可以和创建类型不一样

```java
@Configuration
public class MainConfiguration4 {

    //FactoryBean通过id直接拿到的就是我们需要的Color类  applicationContext.getBean("color");
    //FactoryBean本身不会放在IOC容器中，但是可以在这样获取 applicationContext.getBeansOfType(ColorFactoryBean.class);
    @Bean("color") //通过此id获取的其实是Color类
    public ColorFactoryBean getFactoryBean() {
        System.out.println("创建一个ColorFactoryBean");
        return new ColorFactoryBean();
    }
}
```

***测试：***

```java
@Test
public void testFactoryBean() throws Exception {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiguration4.class);

    //通过id 实际上获取的是ColorFactoryBean.getObject()方法返回的Color
    Color color = (Color) applicationContext.getBean("color");
    System.out.println(color);


    //获取ColorFactoryBean 本身是这样获取，通过类型(FactoryBean本身不会注册到IOC容器中)
    Map<String, ColorFactoryBean> factoryBeanMap = applicationContext.getBeansOfType(ColorFactoryBean.class);
    System.out.println(factoryBeanMap);

    String[] names = applicationContext.getBeanDefinitionNames();
    for (String name : names) {
        System.out.println(name);
    }
}
```

# 二：IOC容器的Bean生命周期

## 8、Bean生命周期

Bean的生命周期：
 * bean创建--初始化---销毁的过程

 * Bean都是由IOC容器创建的，但是我们可以自定义bean的初始化和销毁方法

 * init初始化方法，我们指定后IOC启动时就会自动调用（单实例）

 * init初始化方法，我们获取bean对象时，IOC才会调用（多实例）

 * destroy销毁方法，只有在IOC关闭后才会调用`applicationContext.close();`（单实例）

   > 注：ApplicationContext没有close方法，只有其子类才有销毁方法

 * IOC容器不会管理Bean，容器关闭时不会自动调用销毁方法。所以你只能手动调用destroy方法（多实例）

### ***Bean初始化和销毁的四种方法：***

#### 单实例/多实例下Bean生命过程

 * 构造（创建）

   `单实例：在容器启动的时候创建对象`

   `多实例：在每次获取的时候创建对象`

 * 初始化

   `单实例：对象创建后，并赋好值，调用初始化方法...`

   `多实例：在调用getBean方法时，对象才创建，并赋好值，调用初始化方法...`

 * 销毁

   `单实例：在IOC容器关闭时销毁`

   `多实例：IOC容器不会管理Bean，容器关闭时不会自动调用销毁方法。所以你只能手动调用destroy方法`

#### I：通过@Bean 指定`init`和`destroy`方法

==单实例/多实例下Bean生命过程 同上↑==

***bean类：***

```java
package com.ly.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName：Car.java
 * Author：Ly
 * Date：2022/7/3
 * Description：
 */
public class Car {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Car() {
        System.out.println(dateFormat.format(new Date()) + " 一个car对象被创建");
    }


    /**
     * 自定义Car的初始化方法 （不能有参数）
     */
    public void init() {
        System.out.println(dateFormat.format(new Date()) + "  car对象 init");
    }


    /**
     * 自定义Car销毁方法 （不能有参数）
     */
    public void destroy() {
        System.out.println(dateFormat.format(new Date()) + "  car对象 destroy");
    }
}
```

***配置类：***

```java
/**
 * 1、Bean的生命周期：
 *     bean创建--初始化---销毁的过程
 *
 * 2、Bean是由IOC容器创建的，但是我们可以自定义bean的初始化和销毁方法
 * 3、生命过程
 *       * 构造（创建）
 *          * 单实例：在容器启动的时候创建对象
 *          * 多实例：在每次获取的时候创建对象
 *
 *       * 初始化
 *          * 单实例：对象创建后，并赋好值，调用初始化方法...
 *          * 多实例：在调用getBean方法时，对象才创建，并赋好值，调用初始化方法...
 *
 *       * 销毁
 *          * 单实例：在IOC容器关闭时销毁
 *          * 多实例：IOC容器不会管理Bean，容器关闭时不会自动调用销毁方法。所以你只能手动调用destroy方法
 * 4、bean的初始化和销毁方法
 *    1).指定初始化和销毁方法
 *         + xml配置中有init-method 和destroy-method
 *         + xml配置中有init-method 和destroy-method
 *    2).
 *    3).
 *    4).
 */
@Configuration
public class MainConfigLifeCycle {

    @Bean(value = "car",initMethod = "init",destroyMethod = "destroy")
    public Car getCar() {
        return new Car();
    }
}
```

***测试：***

```java
@Test
public void testBeanLifeCycle() throws Exception {
    //只有子类才有close方法
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
    //applicationContext.getBean("cat");
    //手动销毁IOC容器
    applicationContext.close();
}
```

#### II：InitializingBean接口实现初始化方法，DisposableBean接口实现销毁方法

==单实例/多实例下Bean生命过程 同上↑==

***bean类：实现两个接口***

```java
package com.ly.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName：Cat.java
 * Author：Ly
 * Date：2022/7/3
 * Description：
 */
public class Cat implements InitializingBean, DisposableBean {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Cat() {
        System.out.println(dateFormat.format(new Date()) + " 一个cat对象被创建");

    }

    /**
     * bean对象销毁方法
     * @throws Exception 异常
     */
    @Override
    public void destroy() throws Exception {
        System.out.println(dateFormat.format(new Date()) + "  car对象 destroy");
    }

    /**
     * bean对象初始化方案
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(dateFormat.format(new Date()) + "  car对象 init");
    }
}
```

***配置类：***

```java

/**
 * 1、Bean的生命周期：
 *     bean创建--初始化---销毁的过程
 *
 * 2、Bean是由IOC容器创建的，但是我们可以自定义bean的初始化和销毁方法
 * 3、生命过程
 *       * 构造（创建）
 *          * 单实例：在容器启动的时候创建对象
 *          * 多实例：在每次获取的时候创建对象
 *
 *       * 初始化
 *          * 单实例：对象创建后，并赋好值，调用初始化方法...
 *          * 多实例：在调用getBean方法时，对象才创建，并赋好值，调用初始化方法...
 *
 *       * 销毁
 *          * 单实例：在IOC容器关闭时销毁
 *          * 多实例：IOC容器不会管理Bean，容器关闭时不会自动调用销毁方法。所以你只能手动调用destroy方法
 * 4、bean的初始化和销毁方法
 *    1).指定初始化和销毁方法
 *         + xml配置中有init-method 和destroy-method
 *         + xml配置中有init-method 和destroy-method
 *    2).让Bean实现InitializingBean接口，Bean对象创建时会自动调用其afterPropertiesSet也就是销毁方法
*        让Bean实现DisposableBean接口，IOC容器销毁时会自动调用其destroy方法
 *        多实例单实例区别同 1)
 *    3).
 *    4).
 */
@Configuration
public class MainConfigLifeCycle {

    @Scope("prototype")
    @Bean(value = "cat")
    public Cat getCat() {
        return new Cat();
    }

}
```

#### III ： 使用JSR250规范中注解：(jdk1.8之后没有了，需要添加依赖)

==单实例/多实例下Bean生命过程 同上↑==

 *          注解` @PostConstruct`：在bean创建完成并且属性赋值完成后，执行被此注解标注的初始化方法
 *          注解 `@PreDestroy`：当Bean从IOC容器中移除时，调用被此注解标注的销毁方法

JDK1.8以后不执行这两个注解了，需要添加运行依赖：

```xml
<!-- 高版本的JDK 不支持@PostConstruct 和 @PreDestroy，需要添加依赖 -->
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
```

***Bean使用 这两个注解：***

```java
package com.ly.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * FileName：Dog.java
 * Author：Ly
 * Date：2022/7/3
 * Description：
 */
public class Dog {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Dog() {
        System.out.println(dateFormat.format(new Date()) + " 一个dog对象被创建");
    }

    /**
     * dog对象创建并复制之后调用
     */
    @PostConstruct
    public void init() {
        System.out.println(dateFormat.format(new Date()) + "  dog对象 init");
    }


    /**
     * 对象从IOC中移除前调用此方法
     */
    @PreDestroy
    public void destroy() {
        System.out.println(dateFormat.format(new Date()) + "  dog对象 destroy");
    }
}
```

***配置类：***

```java
package com.ly.config;

import com.ly.bean.Car;
import com.ly.bean.Cat;
import com.ly.bean.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * FileName：MainConfigLifeCycle.java
 * Author：Ly
 * Date：2022/7/3
 * Description： 研究Bean的生命周期
 */

/**
 * 1、Bean的生命周期：
 *     bean创建--初始化---销毁的过程
 *
 * 2、Bean是由IOC容器创建的，但是我们可以自定义bean的初始化和销毁方法
 * 3、生命过程
 *       * 构造（创建）
 *          * 单实例：在容器启动的时候创建对象
 *          * 多实例：在每次获取的时候创建对象
 *
 *       * 初始化
 *          * 单实例：对象创建后，并赋好值，调用初始化方法...
 *          * 多实例：在调用getBean方法时，对象才创建，并赋好值，调用初始化方法...
 *
 *       * 销毁
 *          * 单实例：在IOC容器关闭时销毁
 *          * 多实例：IOC容器不会管理Bean，容器关闭时不会自动调用销毁方法。所以你只能手动调用destroy方法
 * 4、bean的初始化和销毁方法
 *    1).指定初始化和销毁方法
 *         + xml配置中有init-method 和destroy-method
 *         + xml配置中有init-method 和destroy-method
 *    2).让Bean实现InitializingBean接口，Bean对象创建时会自动调用其afterPropertiesSet也就是销毁方法
*        让Bean实现DisposableBean接口，IOC容器销毁时会自动调用其destroy方法
 *        多实例单实例区别同 1)
 *    3).使用JSR250规范中注解：(jdk1.8之后没有了，需要添加依赖)
 *          注解 @PostConstruct：在bean创建完成并且属性赋值完成后，执行被此注解标注的初始化方法
 *          注解 @PreDestroy：当Bean从IOC容器中移除时，调用被此注解标注的销毁方法
 *    4).
 */
@Configuration
public class MainConfigLifeCycle {

    @Scope("prototype")
    @Bean(value = "dog")
    public Dog getCat() {
        return new Dog();
    }

}
```

#### IV：BeanPostProcessor：所有Bean的后置处理器

***对于要注册所有Bean（包括Spring的）都会执行后置处理器，都会执行***

==对扫描到的要注册的*所有Bean*都会执行前/后处理器的两个方法==

***前三个方法都是是init和destroy方法，最后一个不是***

***前/后置处理器`postProcessBeforeInitialization`会在初始化之前，`postProcessAfterInitialization`会在初始化之后被调用***

***后置处理器：***

```java
package com.ly.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * FileName：MyBeanPostProcessor.java
 * Author：Ly
 * Date：2022/7/3
 * Description： 前置/后置处理器
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     *  在bean初始化之前进行进行处理
     * @param bean 就是我们创建的bean新实例
     * @param beanName 就是我们创建的bean新实例的名字
     * @return 要使用的bean实例（可以是原来的，也可以是你自己包装好的）;如果为null，则不会调用后续的BeanPostProcessors函数
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("前置处理器 -- --- 新实例bean = " + bean + " ,名字为：" + beanName);
        return bean;
    }


    /**
     *  在bean初始化之前进行进行处理
     * @param bean 就是我们创建的bean新实例
     * @param beanName 就是我们创建的bean新实例的名字
     * @return 要使用的bean实例（可以是原来的，也可以是你自己包装好的）;如果为null，则不会调用后续的BeanPostProcessors函数
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("后置处理器 -- --- 新实例bean = " + bean + " ,名字为：" + beanName);
        return bean;
    }
}
```
***配置类：***

```java
package com.ly.config;

import com.ly.bean.Car;
import com.ly.bean.Cat;
import com.ly.bean.Dog;
import com.ly.bean.MyBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * FileName：MainConfigLifeCycle.java
 * Author：Ly
 * Date：2022/7/3
 * Description： 研究Bean的生命周期
 */

/**
 * 1、Bean的生命周期：
 *     bean创建--初始化---销毁的过程
 *
 * 2、Bean是由IOC容器创建的，但是我们可以自定义bean的初始化和销毁方法
 * 3、生命过程
 *       * 构造（创建）
 *          * 单实例：在容器启动的时候创建对象
 *          * 多实例：在每次获取的时候创建对象
 *
 *       * 初始化
 *          * 单实例：对象创建后，并赋好值，调用初始化方法...
 *          * 多实例：在调用getBean方法时，对象才创建，并赋好值，调用初始化方法...
 *
 *       * 销毁
 *          * 单实例：在IOC容器关闭时销毁
 *          * 多实例：IOC容器不会管理Bean，容器关闭时不会自动调用销毁方法。所以你只能手动调用destroy方法
 * 4、bean的初始化和销毁方法
 *    1).指定初始化和销毁方法
 *         + xml配置中有init-method 和destroy-method
 *         + xml配置中有init-method 和destroy-method
 *    2).让Bean实现InitializingBean接口，Bean对象创建时会自动调用其afterPropertiesSet也就是销毁方法
*        让Bean实现DisposableBean接口，IOC容器销毁时会自动调用其destroy方法
 *        多实例单实例区别同 1)
 *    3).使用JSR250规范中注解：(jdk1.8之后没有了，需要添加依赖)
 *          注解 @PostConstruct：在bean创建完成并且属性赋值完成后，执行被此注解标注的初始化方法
 *          注解 @PreDestroy：当Bean从IOC容器中移除时，调用被此注解标注的销毁方法
 *    4).BeanPostProcessor:bean后置处理器
 *          postProcessBeforeInitialization方法：在所有bean初始化之前进行进行处理
 *          postProcessAfterInitialization方法：在所有bean初始化之后进行进行处理
 */
@ComponentScan(value = "com.ly.bean",includeFilters = {@ComponentScan.Filter(value = {Component.class})})
@Configuration
public class MainConfigLifeCycle {

    @Bean(value = "car",initMethod = "init",destroyMethod = "destroy")
    public Car getCar() {
        return new Car();
    }

    @Bean(value = "cat")
    public Cat getCat() {
        return new Cat();
    }

    @Bean(value = "dog")
    public Dog getDog() {
        return new Dog();
    }

}
```
***测试结果：***

<img src=".\img\image-20220703160838544.png" alt="image-20220703160838544" style="zoom:200%;" />


> 注：如果还是用@Bean注解获取的话，会报错。***意思就是：配置类mainConfigLifeCycle不符合后置处理器处理的条件，即无法AOP操作***
>
> `信息: Bean 'mainConfigLifeCycle' of type [com.ly.config.MainConfigLifeCycle$$EnhancerBySpringCGLIB$$47f77e80] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)`
>
> ***解决方法：可以使用@Lazy懒加载***

#### 总结：

​	I，II，III，IV中每个方法的调用步骤：

```java
1.调用Bean的无参构造器方法
2.初始化之前调用 前置/后置处理器的postProcessBeforeInitialization()方法//注册到IOC容器中的每个bean
3.调用InitializingBean接口的afterPropertiesSet()方法//就是初始化方法
4.调用 前置/后置处理器的postProcessAfterInitialization()方法//注册到IOC容器中的每个bean
5.所有创建完，统一销毁
```

***部分源码：***

```java
//AbstractAutowireCapableBeanFactory#doCreateBean方法
... //0.通过无参构造器创建bean对象
try {
    //1、对属性进行赋值
    populateBean(beanName, mbd, instanceWrapper);
    exposedObject = initializeBean(beanName, exposedObject, mbd);
}

//initializeBean方法在 AbstractAutowireCapableBeanFactory类中
if (mbd == null || !mbd.isSynthetic()) {
    //2、先调用前置处理器
    wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
}

try {
    //3、调用初始化方法
    invokeInitMethods(beanName, wrappedBean, mbd);
}
...
if (mbd == null || !mbd.isSynthetic()) {
    //4、调用后置处理器方法
    wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
}

```

#### 额外知识：BeanPostProcessor应用

***1：在任一Bean中获取IOC容器获取其他属性（后置处理器）***

==就是上面第二步：2、调用前置处理器中调用传递的==

如：Dog类实现ApplicationContextAware接口

```java
public class Dog  implements ApplicationContextAware{
    /**
     * 通过ApplicationContextAware接口 获取IOC容器,这个功能就是ApplicationContextAwareProcessor类实现的
     * @param applicationContext IOC容器
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getEnvironment();
        applicationContext.getBean("");
    }
}
```

调用栈：

![image-20220703165100149](.\img\image-20220703165100149.png)



***2：web下的数据校验***、

```java
public class BeanValidationPostProcessor implements BeanPostProcessor, InitializingBean {
    ...
    //初始化之前数据校验
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!this.afterInitialization) {
            this.doValidate(bean);}

        return bean;
    }
	//初始化之后数据校验
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.afterInitialization) {
            this.doValidate(bean);
        }

        return bean;
    }
   ...  
}
```

***3、处理方法III中@PostConstruct初始化和@PreDestroy销毁注解***

通过前/后置处理器，调用 注解

```java
public class InitDestroyAnnotationBeanPostProcessor{
        //前置处理器
		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {}
	//后置处理器
    	public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {

}
```
***对@Autowire注解调用：***

```java
public class AutowiredAnnotationBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor,
      MergedBeanDefinitionPostProcessor, PriorityOrdered, BeanFactoryAware {
          
          ...          
      }
```

等等：底层大量应用 后置处理器

![image-20220703171719438](.\img\image-20220703171719438.png)

# 三：IOC容器属性赋值

## 9、@Value 属性赋值

​	对基础属性进行赋值

### 赋值用法1：基本数值赋值

### 赋值用法2：可以写SpringEL即Spring表达式 #{表达式}

```java
public class Person {

    /**
     * 使用@Value赋值：
     *    1、基本数值
     *    2、可以写SpringEL即Spring表达式 #{}
     *    3、
     *    4、
     */
    @Value("张三") //基本数值赋值
    private String name;
    @Value("#{20-2}") //SpringEL表达式赋值
    private Integer age;
    
    public Person() {
    }
}
```

### 赋值用法3：引入外部配置文件，使用${key}赋值

前提：此方法和Spring的xml配置文件引入外部文件，${key}赋值完全一样

```xml
<!-- xml配置文件-->
<context:property-placeholder location="classpath:person.properties" />
<bean id="person" class="com.ly.bean.Person">
    <property name="name" value="张三" />
    <property name="age" value="18" />
    <!-- ${key}引用-->
    <property name="rickName" value="${person.rickName}" />
</bean>
```

#### 借助`@PropertySource`注解，实现外部文件引入

​	一般都在配置类上引入配置文件！

***配置类：MainConfigOfPropertyValues.java***

```java

/**
 * 注解@PropertySource
 *     等价于xml中引入外部文件： <context:property-placeholder location="classpath:person.properties" />
 *    参数：
 *      name:名称，没啥用。IOC容器中也不回有这个名字的对象，取值还是直接properties文件中的key ${key}
 *      value:配置文件数据classpath类路径 或file文件路径
 *      encoding：配置文件编码
 *      ignoreResourceNotFound：没找到配置文件是否忽略？默认false，会报错
 *      factory：待定
 */
@PropertySource(name = "personProperty",value = {"classpath:person.properties"},encoding = "utf-8")
@Configuration
public class MainConfigOfPropertyValues {

    @Bean("onePerson")
    public Person getPerson(){
        return new Person();
    }
}
```

***``@Value`使用配置文件***

```java
public class Person {

    /**
     * 使用@Value赋值：
     *    1、基本数值
     *    2、可以写SpringEL即Spring表达式 #{}
     *    3、使用${}取出配置文件中[properties文件]的值（也就是IOC容器中环境Environment变量的值）
     *    4、
     */
    @Value("张三")
    private String name;
    @Value("#{20-2}")
    private Integer age;
    @Value("${person.rickName}") //外部文件引入方法，直接配置文件的key即可
    private String rickName;
    public Person() {
    }
}
```

> 注：`@PropertySources()` 和`@PropertySource()`的关系与`@ComponentScan`和`@ComponentScan`关系一样

### 赋值用法4：第三种方法的扩展

​	因为引入的**外部配置文件**均会被加载到IOC容器的Environment环境变量中，所以也可以由此获取。

```java
//获通过IOC容器的环境变量获取配置文件数据 【key就是配置文件的key，不变】
String rickName = applicationContext.getEnvironment().getProperty("person.rickName");
System.out.println(rickName);
```



# 四：IOC容器的自动装配

## 10、@Autowire 自动注入

***@Autowire源码：***

```java
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    boolean required() default true;//默认一定要找到，如果找不到就会报错
}
```

***自动装配***：Spring利用依赖注入(DI)，完成对IOC容器中各个组件的依赖关系赋值。

> 将容器中的组件自动注入到相关依赖中，前提是IOC容器中有

@Autowire自动注入规则：

+ 优先按照`类型(byType)`去容器中查找已存在的组件

  + 如果找到多个`同类型`的，就按照`名字(byName)`找（名字就是你定义时取得）

    ```java
    @Autowired
    private BookDao bookDao; //如果是afafa，就找IOC容器中名afafa 类型为BookDao的组件
    ```

  + 如果没找到，就报错（默认`required=true`）

  > `@Autowire`注解完成自动装配的底层代码就是`AutowiredAnnotationBeanPostProcessor.java`代码，不难看出这又是PostProcessor后置处理器。

## 11、@Qualifier 需要代培@Autowire使用

***@Qualifier源码：***

```java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";//组件名字
}
```

按照指定名称实现自动注入

```java
@Qualifier("bookDao1")
@Autowired(required=false)
private BookDao bookDao;
```



## 12、@Primary  一般和@Autowire搭配使用

如`BookDao`这个类型的组件在IOC容器中有多个，使用这个注解可以让Spring进行自动装配的时候，默认选择`@Primary`注解标注的Bean组件

***@Primary源码：***

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Primary {

}
```

***例子：***

配置类：MainConfigOfAutowire.java

```java
@ComponentScan({"com.ly.controller","com.ly.dao","com.ly.service"})//BookDao 有注解@Repository
@Configuration
public class MainConfigOfAutowire {
    
    @Primary //默认首选bookDao2这个组件
    @Bean("bookDao2")
    public BookDao getBookDao(){
        return new BookDao();
    }
}
```

依赖类：BookService.java

```java
@Primary //优先选择bookService，当前这个组件
@Service
public class BookService {
    //@Qualifier //有注解@Primary就没必要指定组件了
    @Autowired
    private BookDao bookDao; //优先选择bookDao2这个组件
}
```

***@Qualifier和@Primary的优先级***

​	有`@Qualifier`注解的先找满足这个注解的，如果没有`@Qualifier`就首选`@Primary`注解的。

## 13、@Resourece(JSR250) –Java规范

​	可以和@Autowire一样实现自动装配，默认是按照组件(如：bookDao)名称进行装配的。当然你可以指定name（`@Resource(name="bookDao2")`）进行装配。

***先根据名字找，如果IOC容器中没有这个属性名，再根据类型找。如果没有这个属性名（根据类型找时），@Primary就会生效***

缺点：

+ `无法和Spring注解@Primary组合使用（@Primary会失效）`
+ `必须找到，如果找不到就会报错（没有@Autowire的reqired属性）`

```java
public class BookService {

    @Resource
    //Qualifier
    //@Autowired
    private BookDao bookDao;
}
```



## 14、@Inject(JSR330)  –Java规范

​	==使用和@Autowire完全一样，就是没有`required`属性，即默认没找到就会报错==

使用需要先导入依赖：

```xml
<!-- https://mvnrepository.com/artifact/javax.inject/javax.inject -->
<dependency>
    <groupId>javax.inject</groupId>
    <artifactId>javax.inject</artifactId>
    <version>1</version>
</dependency>

```

***源码：***

```java
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
}
```



## 15、


























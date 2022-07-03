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

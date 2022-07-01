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

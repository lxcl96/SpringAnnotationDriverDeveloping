package com.ly.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * FileName:LogAspects.class
 * Author:ly
 * Date:2022/7/7
 * Description:
 */
@Aspect//告诉Spring当前类是一个切面类
public class LogAspects {
    /**
     * 抽取公共的切入点表达式，不需要用具体方法
     *      如果本类引用，直接调用方法即可（注意加引号）。 如：@AfterReturning("pointCut()")
     *      如果是外部类引用，则需要写全类名的方法调用（注意加引号）  如：@AfterThrowing("* com.ly.aop.LogAspects.pointCut()")
     */
    @Pointcut("execution(* com.ly.aop.MathCalculator.div(..))")
    public void pointCut(){};

    //目标方法运行前切入，需要制定切入点表达式
    @Before("execution(public int com.ly.aop.MathCalculator.div(int,int))")
    //给定形参，并在注解中指定（名字必须一样）IOC容器会自动注入
    public void logStart(JoinPoint joinPoint) {
//        System.out.println(joinPoint.getKind());//method-execution
//        System.out.println(joinPoint.getSignature());//获取签名int com.ly.aop.MathCalculator.div(int,int)
//        System.out.println(joinPoint.getSignature().getDeclaringType());//返回类类型class com.ly.aop.MathCalculator
//        System.out.println(joinPoint.getSignature().getDeclaringTypeName());//返回类名com.ly.aop.MathCalculator
//        System.out.println(joinPoint.getSignature().getModifiers());//返回修饰符 1
//        System.out.println(joinPoint.getSignature().getName());//返回方法名 div
//        System.out.println(joinPoint.getSourceLocation());
//        System.out.println(joinPoint.getSourceLocation().getFileName());
//        System.out.println(joinPoint.getSourceLocation().getLine());
//        System.out.println(joinPoint.getSourceLocation().getWithinType());// class com.ly.aop.MathCalculator
//        System.out.println(joinPoint.getStaticPart());//execution(int com.ly.aop.MathCalculator.div(int,int))
//        System.out.println(joinPoint.toLongString());//execution(public int com.ly.aop.MathCalculator.div(int,int))
//        System.out.println(joinPoint.toShortString());//execution(MathCalculator.div(..))
//        System.out.println(Arrays.toString(joinPoint.getArgs()));//[2, 1]
//        System.out.println(joinPoint.getThis());//com.ly.aop.MathCalculator@2a54a73f
//        System.out.println(joinPoint.getTarget());//com.ly.aop.MathCalculator@2a54a73f

        System.out.println(joinPoint.getSignature().getName() + "运行。。。参数列表是：{" + Arrays.toString(joinPoint.getArgs()) + "}");
    }

    @After("execution(* com.ly.aop.MathCalculator.*(..))")
    public void logStop(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "结束。。。");
    }

    @AfterReturning(value = "pointCut()",returning = "ret")
    //给定形参，并在注解中指定（名字必须一样）IOC容器会自动注入
    public void logReturn(JoinPoint joinPoint,Object ret) {
        System.out.println(joinPoint.getSignature().getName() + "正常返回。。。运行结果：{" + ret + "}");
    }

    @AfterThrowing(value = "com.ly.aop.LogAspects.pointCut()",throwing = "exception")
    //给定形参，并在注解中指定（名字必须一样）IOC容器会自动注入
    public void logException(JoinPoint joinPoint,Exception exception) {
        System.out.println(joinPoint.getSignature().getName() + "运行异常。。。异常信息：{" + exception + "}");
    }
}

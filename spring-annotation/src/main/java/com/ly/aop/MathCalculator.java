package com.ly.aop;

/**
 * FileName:MathCalculator.class
 * Author:ly
 * Date:2022/7/7
 * Description:
 */
public class MathCalculator {
    public int div(int i,int j) {

        System.out.println(String.format("i=%d，j=%d",i,j));
        return i/j;
    }
}

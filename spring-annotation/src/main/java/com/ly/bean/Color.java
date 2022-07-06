package com.ly.bean;

import org.springframework.context.ApplicationContext;

/**
 * FileName：Color.java
 * Author：Ly
 * Date：2022/7/2
 * Description：
 */
public class Color {
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color{" +
                "car=" + car +
                '}';
    }
}

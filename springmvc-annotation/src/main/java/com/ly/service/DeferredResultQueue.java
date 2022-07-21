package com.ly.service;

import org.springframework.web.context.request.async.DeferredResult;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * FileName:DeferredResultQueue.class
 * Author:ly
 * Date:2022/7/21
 * Description: 存放DeferredResult消息对象
 */
public class DeferredResultQueue {

    private static Queue<DeferredResult<Object>> deferredResultQueue = new ConcurrentLinkedDeque<>();

    public static void save(DeferredResult<Object> deferredResult) {
        deferredResultQueue.add(deferredResult);
    }

    public static DeferredResult<Object> get() {
        return deferredResultQueue.poll();
    }
}

package com.ly.ext.eventDeveloping;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * FileName:UserRegisterEvent.class
 * Author:ly
 * Date:2022/7/13
 * Description:
 */
public class UserOnLineEvent extends ApplicationEvent {
    public UserOnLineEvent(Object source) {
        super(source);
    }

    public UserOnLineEvent(Object source, Clock clock) {
        super(source, clock);
    }
}

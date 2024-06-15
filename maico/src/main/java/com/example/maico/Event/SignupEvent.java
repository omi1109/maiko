package com.example.maico.Event;

import org.springframework.context.ApplicationEvent;

import com.example.maico.entity.User;

import lombok.Getter;

@Getter
public class SignupEvent extends ApplicationEvent {
    private User user;
    private String requestUrl;        

    public SignupEvent(Object source, User user, String requestUrl) {
        super(source);
        
        this.user = user;        
        this.requestUrl = requestUrl;
    }
}
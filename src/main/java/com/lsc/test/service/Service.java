package com.lsc.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private ServiceB serviceB;

//    @Async
    public void a(){
        System.out.println(Thread.currentThread().getName()+"   7777777777777777777");
        c();
    }

//    @Async
    public void c(){
        System.out.println(Thread.currentThread().getName()+"   999999999999999999999");
    }

}

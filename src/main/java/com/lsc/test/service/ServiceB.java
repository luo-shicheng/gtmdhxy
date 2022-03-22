package com.lsc.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    @Autowired
    private com.lsc.test.service.Service service;

//    @Async
    public void b(){
        System.out.println(Thread.currentThread().getName()+"   88888888888888888888");
    }

}

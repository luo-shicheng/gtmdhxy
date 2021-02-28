package com.lsc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;


@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.lsc.test.rs"})
//@ComponentScan(basePackages="com.lsc.test.rs",useDefaultFilters = false,includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {RestController.class})})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @EventListener(WebServerInitializedEvent.class)
    public void onReady(WebServerInitializedEvent event){
        System.out.println(event.getClass().getName());
        WebServerApplicationContext applicationContext = event.getApplicationContext();
    }

}

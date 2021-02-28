package com.lsc.test.config;

import com.lsc.test.model.UrlBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public UrlBody urlBody(){
        return new UrlBody();
    }
}

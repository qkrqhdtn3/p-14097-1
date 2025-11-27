package com.back;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    int version(){
        return 55;
    }

    public ApplicationRunner applicationRunner(){
        return new MyApplicationRunner();
    }

    public ApplicationRunner applicationRunner2(){
        return new MyApplicationRunner();
    }
}
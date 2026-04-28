package com.fiap.salasreuniao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SalasReuniaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalasReuniaoApplication.class, args);
    }
}

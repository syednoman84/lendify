package com.example.servicingaccountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.example.servicingaccountservice.client")
@SpringBootApplication
public class ServicingAccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicingAccountServiceApplication.class, args);
    }
}


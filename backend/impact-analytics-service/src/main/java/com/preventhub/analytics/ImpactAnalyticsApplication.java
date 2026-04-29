package com.preventhub.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ImpactAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImpactAnalyticsApplication.class, args);
    }
}

package com.preventhub.engagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EngagementTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngagementTrackingApplication.class, args);
    }
}

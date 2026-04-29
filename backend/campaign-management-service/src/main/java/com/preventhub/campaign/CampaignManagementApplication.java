package com.preventhub.campaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CampaignManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampaignManagementApplication.class, args);
    }
}

package com.capstone.client.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class CapstoneOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CapstoneOrderApplication.class, args);
    }
}

package com.capstone.client.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CapstoneUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapstoneUserApplication.class, args);
    }

}

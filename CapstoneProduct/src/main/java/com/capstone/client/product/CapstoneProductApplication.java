package com.capstone.client.product;

import com.capstone.client.product.domain.repository.FileRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.Resource;

@SpringBootApplication
@EnableEurekaClient
public class CapstoneProductApplication implements CommandLineRunner {
    @Resource
    FileRepo fileRepo;

    @Override
    public void run(String... arg) {
        fileRepo.init();
    }

    public static void main(String[] args) {
        SpringApplication.run(CapstoneProductApplication.class, args);
    }
}

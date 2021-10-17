package com.freedom.services.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.freedom.services"})
@SpringBootApplication(scanBasePackages = {"com.freedom"})
@EnableJpaRepositories(basePackages="com.freedom")
@EntityScan(basePackages="com.freedom")
public class TestServiceApplication {


    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(TestServiceApplication.class, args);
    }

}



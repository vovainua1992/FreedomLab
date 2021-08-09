package com.freedom.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.freedom.web","com.freedom.services"})
@SpringBootApplication(scanBasePackages = {"com.freedom.web","com.freedom"})
@EnableJpaRepositories(basePackages="com.freedom")
@EntityScan(basePackages="com.freedom")
public class SpringmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmvcApplication.class, args);
	}

}

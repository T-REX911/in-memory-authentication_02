package com.spring.SpringSecurity_02.SpringSecurity_02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class})
public class SpringSecurity02Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity02Application.class, args);
	}

}

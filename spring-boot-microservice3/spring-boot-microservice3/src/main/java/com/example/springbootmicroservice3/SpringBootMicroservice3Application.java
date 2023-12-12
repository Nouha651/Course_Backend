package com.example.springbootmicroservice3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableFeignClients
@SpringBootApplication

public class SpringBootMicroservice3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMicroservice3Application.class, args);
	}

}

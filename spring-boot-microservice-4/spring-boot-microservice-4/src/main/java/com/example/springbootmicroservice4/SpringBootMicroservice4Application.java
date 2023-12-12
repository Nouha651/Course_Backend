package com.example.springbootmicroservice4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringBootMicroservice4Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMicroservice4Application.class, args);
	}

}

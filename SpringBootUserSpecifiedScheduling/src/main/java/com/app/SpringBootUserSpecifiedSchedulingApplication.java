package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class SpringBootUserSpecifiedSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootUserSpecifiedSchedulingApplication.class, args);
	}

}

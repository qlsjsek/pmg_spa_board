package com.pmg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PmgApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmgApplication.class, args);
	}

}

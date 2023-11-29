package com.crud.spring.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.crud.spring.bank")
public class InterbankTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterbankTransferApplication.class, args);
	}

}

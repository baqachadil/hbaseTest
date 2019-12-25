package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.services.TestService;

@SpringBootApplication
public class HbaseCnxApplication{

	public static void main(String[] args) {
		SpringApplication.run(HbaseCnxApplication.class, args);
	}
	
	

}

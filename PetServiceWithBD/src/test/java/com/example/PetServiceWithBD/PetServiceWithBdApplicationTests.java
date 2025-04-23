package com.example.PetServiceWithBD;


import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

class PetServiceWithBdApplicationTests {
	public static void main(String[] args) {
		SpringApplication.from(PetServiceWithBdApplicationTests::main).with(TestcontainersConfiguration.class).run(args);
	}
}

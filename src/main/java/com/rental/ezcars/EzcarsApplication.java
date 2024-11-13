package com.rental.ezcars;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Jitendra Rawat
 */
@SpringBootApplication
@EnableScheduling
public class EzcarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzcarsApplication.class, args);
		System.out.print("Welcome to EzCars!!");
	}

}

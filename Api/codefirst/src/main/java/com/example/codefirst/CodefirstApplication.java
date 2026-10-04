package com.example.codefirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CodefirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodefirstApplication.class, args);
	}

}

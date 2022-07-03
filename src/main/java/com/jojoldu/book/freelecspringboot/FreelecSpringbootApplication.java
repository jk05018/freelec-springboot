package com.jojoldu.book.freelecspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FreelecSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelecSpringbootApplication.class, args);
	}

}

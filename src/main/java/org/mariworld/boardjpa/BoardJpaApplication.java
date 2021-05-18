package org.mariworld.boardjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
@EnableJpaAuditing
public class BoardJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardJpaApplication.class, args);
	}

}

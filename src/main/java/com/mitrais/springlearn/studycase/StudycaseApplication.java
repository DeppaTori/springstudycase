package com.mitrais.springlearn.studycase;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;



@SpringBootApplication

@EnableJpaRepositories
public class StudycaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudycaseApplication.class, args);
	}
	

	
	

}

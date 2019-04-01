package com.mitrais.springlearn.studycase;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.mitrais.springlearn.studycase.test.ProductService;

@Profile("Test")
@Configuration
public class TestConfiguration {

	@Bean
	@Primary
	public ProductService productService() {
		return Mockito.mock(ProductService.class);
	}
}

package com.shorterurl.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.shorterurl.service.validation")
public class UrlValidatorConfig {

	@Bean
	public UrlValidator urlValidator() {
		return new UrlValidator();
	}
}

package com.shorterurl.service.validation;

import org.springframework.stereotype.Component;

@Component
public interface IValidator {

	public void validate(String url);
}

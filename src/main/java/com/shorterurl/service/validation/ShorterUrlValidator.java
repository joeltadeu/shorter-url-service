package com.shorterurl.service.validation;

import com.shorterurl.service.exception.ValidationErrorException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ShorterUrlValidator {

	private final UrlValidator urlValidator;

	public ShorterUrlValidator() {
		this.urlValidator = new UrlValidator();
	}

	public void validate(String url) {
		if (!StringUtils.hasText(url))
			throw new ValidationErrorException("URL not entered");

		if (!urlValidator.isValid(url))
			throw new ValidationErrorException("URL is invalid");
	}
}

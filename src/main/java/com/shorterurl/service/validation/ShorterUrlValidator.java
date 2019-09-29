package com.shorterurl.service.validation;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.shorterurl.service.exception.ValidationErrorException;

@Component
public class ShorterUrlValidator implements IValidator {

	private final UrlValidator urlValidator;

	public ShorterUrlValidator(UrlValidator urlValidator) {
		this.urlValidator = urlValidator;
	}

	@Override
	public void validate(String url) {
		if (!StringUtils.hasText(url))
			throw new ValidationErrorException("URL not entered");

		if (!urlValidator.isValid(url))
			throw new ValidationErrorException("URL is invalid");
	}
}

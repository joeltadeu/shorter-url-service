package com.shorterurl.service.exception;

public class ValidationErrorException extends RuntimeException {

	private static final long serialVersionUID = 4384920945038295451L;

	public ValidationErrorException(String message) {
		super(message);
	}

	public ValidationErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}

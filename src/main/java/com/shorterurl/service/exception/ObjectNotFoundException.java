package com.shorterurl.service.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4384920945038295451L;

	public ObjectNotFoundException(String message) {
		super(message);
	}

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

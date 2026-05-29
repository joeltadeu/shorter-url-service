package com.shorterurl.service.exception;

import java.io.Serial;

public class ValidationErrorException extends RuntimeException {

  @Serial private static final long serialVersionUID = 4384920945038295451L;

  public ValidationErrorException(String message) {
    super(message);
  }
}

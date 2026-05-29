package com.shorterurl.service.exception;

import java.io.Serial;

public class ObjectNotFoundException extends RuntimeException {

  @Serial private static final long serialVersionUID = 4384920945038295451L;

  public ObjectNotFoundException(String message) {
    super(message);
  }
}

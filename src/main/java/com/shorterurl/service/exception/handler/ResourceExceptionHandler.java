package com.shorterurl.service.exception.handler;

import com.shorterurl.service.exception.ObjectNotFoundException;
import com.shorterurl.service.exception.ValidationErrorException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(
      ObjectNotFoundException e, HttpServletRequest request) {

    StandardError err =
        new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(ValidationErrorException.class)
  public ResponseEntity<StandardError> validationError(
      ValidationErrorException e, HttpServletRequest request) {

    StandardError err =
        new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<StandardError> httpMessageNotReadable(
      HttpMessageNotReadableException e, HttpServletRequest request) {

    StandardError err =
        new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }
}

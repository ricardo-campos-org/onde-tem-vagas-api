package br.com.ondetemvagas.webapp.controller;

import br.com.ondetemvagas.webapp.exception.UserAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

/** This class takes care of possible exceptions that may occur. */
@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(WebExchangeBindException.class)
  ResponseEntity<Map<String, String>> generalException(WebExchangeBindException ex) {
    final Map<String, String> errorMap = new HashMap<>();
    ex.getFieldErrors().forEach(f -> errorMap.put(f.getField(), f.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  ResponseEntity<Map<String, String>> userAlreadyExists(UserAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createMessageMap(ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Map<String, String>> userNotFound(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(createMessageMap(ex.getMessage()));
  }

  private Map<String, String> createMessageMap(String message) {
    final Map<String, String> errorMap = new HashMap<>();
    errorMap.put("message", message);
    return errorMap;
  }
}

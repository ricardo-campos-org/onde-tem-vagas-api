package br.com.ondetemvagas.webapp.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(WebExchangeBindException.class)
  ResponseEntity<Map<String, String>> generalException(WebExchangeBindException ex) {
    final Map<String, String> errorMap = new HashMap<>();
    ex.getFieldErrors().forEach(f -> errorMap.put(f.getField(), f.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
  }

  /*
  @ExceptionHandler(UserNotFoundException.class)
  ResponseEntity<Map<String, String>> userNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createMessageMap(ex.getMessage()));
  }
   */

  private Map<String, String> createMessageMap(String message) {
    final Map<String, String> errorMap = new HashMap<>();
    errorMap.put("message", message);
    return errorMap;
  }
}

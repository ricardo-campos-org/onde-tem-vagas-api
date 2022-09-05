package br.com.ondetemvagas.webapp.exception;

public class UserAlreadyExistsException extends RuntimeException {

  @Override
  public String getMessage() {
    return "User already registered [by his email]!";
  }
}

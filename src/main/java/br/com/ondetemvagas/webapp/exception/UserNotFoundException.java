package br.com.ondetemvagas.webapp.exception;

public class UserNotFoundException extends RuntimeException {

  @Override
  public String getMessage() {
    return "User not found!";
  }
}

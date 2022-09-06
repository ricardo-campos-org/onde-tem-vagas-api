package br.com.ondetemvagas.webapp.exception;

/** This class represents a situation where a user already is registered. */
public class UserAlreadyExistsException extends RuntimeException {

  /**
   * Get the message to show to the user.
   *
   * @return the message to the user
   */
  @Override
  public String getMessage() {
    return "User already registered [by his email]!";
  }
}

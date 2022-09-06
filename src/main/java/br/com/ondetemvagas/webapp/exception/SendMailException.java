package br.com.ondetemvagas.webapp.exception;

/** This class represents any issue related to email senting. */
public class SendMailException extends RuntimeException {

  private final String localMessage;

  /**
   * Create a new exception with a personalized message.
   *
   * @param message the message to be displayed
   */
  public SendMailException(String message) {
    super(message);
    this.localMessage = message;
  }

  @Override
  public String getMessage() {
    return localMessage;
  }
}

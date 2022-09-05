package br.com.ondetemvagas.webapp.exception;

public class SendMailException extends RuntimeException {

  private String localMessage;

  public SendMailException(String message) {
    super(message);
    this.localMessage = message;
  }

  @Override
  public String getMessage() {
    return localMessage;
  }
}

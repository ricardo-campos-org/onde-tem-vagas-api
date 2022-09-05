package br.com.ondetemvagas.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** This class is the entrypoint of the application. */
@SpringBootApplication
public class WebappApplication {

  /**
   * Starts the web application.
   *
   * @param args optional arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(WebappApplication.class, args);
  }
}

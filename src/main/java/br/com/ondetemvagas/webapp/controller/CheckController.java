package br.com.ondetemvagas.webapp.controller;

import br.com.ondetemvagas.webapp.dto.CheckDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** This class expose a check endpoint, like a service health. */
@RestController
public class CheckController {

  @Value("${service.version}")
  private String serviceVersion;

  @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
  public CheckDto check() {
    return CheckDto.builder().message("OK").release(serviceVersion).build();
  }
}

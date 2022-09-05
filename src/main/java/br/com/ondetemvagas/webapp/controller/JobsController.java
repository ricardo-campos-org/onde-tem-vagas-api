package br.com.ondetemvagas.webapp.controller;

import br.com.ondetemvagas.webapp.dto.UserJobDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** This class expose endpoints related to jobs. */
@RestController
@RequestMapping("/jobs")
public class JobsController {

  @GetMapping(value = "/get-by-user-email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserJobDto> getJobsByUser(@RequestParam String email) {
    return new ArrayList<>();
  }
}

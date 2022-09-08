package br.com.ondetemvagas.webapp.controller;

import br.com.ondetemvagas.webapp.dto.CheckDto;
import br.com.ondetemvagas.webapp.entity.PortalJob;
import br.com.ondetemvagas.webapp.service.CrawlerService;
import br.com.ondetemvagas.webapp.service.MailService;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/** This class expose a check endpoint, like a service health. */
@Setter
@RestController
@NoArgsConstructor
public class CheckController {

  @Value("${service.version}")
  private String serviceVersion;

  private MailService mailService;

  private CrawlerService crawlerService;

  @Autowired
  public CheckController(MailService mailService, CrawlerService crawlerService) {
    this.mailService = mailService;
    this.crawlerService = crawlerService;
  }

  @GetMapping("/")
  public String index() {
    return "OK";
  }

  @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
  public CheckDto check() {
    return CheckDto.builder().message("OK").release(serviceVersion).build();
  }

  @PostMapping("/test-email")
  public void testEmail() {
    String name = "Montania";
    String email = "ricardo.montania@gmail.com";

    PortalJob portalJob =
        PortalJob.builder()
            .id(1L)
            .jobTitle("Java Developer")
            .companyName("Daitan Labs")
            .jobType("SR")
            .jobDescription("Come work with us!")
            .publishedAt("Friday, 09-02-2022")
            .jobUrl("https://daitan-labs.com/careers/java-developer.com")
            .portalId(1L)
            .createdAt(LocalDateTime.now())
            .build();

    mailService.jobNotification(name, email, Set.of(portalJob));
  }

  @PostMapping("/test-crawler")
  public void testCrawler() {
    crawlerService.start();
  }
}

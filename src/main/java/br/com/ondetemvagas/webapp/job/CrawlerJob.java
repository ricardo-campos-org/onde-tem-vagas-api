package br.com.ondetemvagas.webapp.job;

import br.com.ondetemvagas.webapp.entity.PortalJob;
import br.com.ondetemvagas.webapp.entity.User;
import br.com.ondetemvagas.webapp.service.CrawlerService;
import br.com.ondetemvagas.webapp.service.JobService;
import br.com.ondetemvagas.webapp.service.MailService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** This class handle the entrypoint for crawl the jobs. */
@Slf4j
@Component
@Setter
@NoArgsConstructor
public class CrawlerJob {

  private CrawlerService crawlerService;

  private JobService jobService;

  private MailService mailService;

  /**
   * Create a new instance of the class.
   *
   * @param crawlerService crawler service that will find the jobs.
   */
  @Autowired
  public CrawlerJob(CrawlerService crawlerService, JobService jobService) {
    this.crawlerService = crawlerService;
    this.jobService = jobService;
  }

  /**
   * Handle the scheduled job search.
   * (0) seconds 0-59
   * (0) minute 0-59
   * (11,16) hour
   * (*) day of the month 1-31
   * (*) month 1-12
   * (*) day of the week 0-7
   */
  @Scheduled(cron = "0 0 11,16 * * 1-6")
  public void reportCurrentTime() {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    log.info("Starting jobs crawler at {}", dateFormat.format(new Date()));
    crawlerService.start();
    log.info("Finished jobs crawler at {}", dateFormat.format(new Date()));

    log.info("Starting new jobs verifications at {}", dateFormat.format(new Date()));
    Map<User, Set<PortalJob>> userSetMap = jobService.processNewJobs();
    log.info("Finished new jobs verifications at {}", dateFormat.format(new Date()));

    // Send notifications
    if (userSetMap.isEmpty()) {
      return;
    }

    for(Map.Entry<User, Set<PortalJob>> entry : userSetMap.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        User user = entry.getKey();
        mailService.jobNotification(user.getFirstName(), user.getEmail(), entry.getValue());
      }
    }
  }
}

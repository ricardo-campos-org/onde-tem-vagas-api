package br.com.ondetemvagas.webapp.job;

import br.com.ondetemvagas.webapp.service.CrawlerService;
import java.text.SimpleDateFormat;
import java.util.Date;
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

  /**
   * Create a new instance of the class.
   *
   * @param crawlerService crawler service that will find the jobs.
   */
  @Autowired
  public CrawlerJob(CrawlerService crawlerService) {
    this.crawlerService = crawlerService;
  }

  /**
   * Handle the scheduled job search.
   */
  @Scheduled(cron = "0 0 */2 * * *")
  public void reportCurrentTime() {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    log.info("Starting jobs crawler at {}", dateFormat.format(new Date()));

    crawlerService.start();
  }
}

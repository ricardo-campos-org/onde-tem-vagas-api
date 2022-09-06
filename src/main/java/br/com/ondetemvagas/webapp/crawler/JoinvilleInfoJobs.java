package br.com.ondetemvagas.webapp.crawler;

import br.com.ondetemvagas.webapp.crawler.shared.InfoJobs;
import br.com.ondetemvagas.webapp.entity.PortalJob;
import java.util.List;
import org.jsoup.nodes.Document;

public class JoinvilleInfoJobs implements Crawler {

  @Override
  public List<PortalJob> findJobs(Document document) {
    InfoJobs infoJobs = new InfoJobs();
    return infoJobs.findJobs(document);
  }
}

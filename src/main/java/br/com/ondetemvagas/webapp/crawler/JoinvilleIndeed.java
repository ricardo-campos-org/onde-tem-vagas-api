package br.com.ondetemvagas.webapp.crawler;

import br.com.ondetemvagas.webapp.crawler.shared.Indeed;
import br.com.ondetemvagas.webapp.entity.PortalJob;
import java.util.List;
import org.jsoup.nodes.Document;

public class JoinvilleIndeed implements Crawler {

  @Override
  public List<PortalJob> findJobs(Document document) {
    Indeed indeed = new Indeed();
    return indeed.findJobs(document);
  }
}

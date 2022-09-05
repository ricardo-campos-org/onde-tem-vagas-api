package br.com.ondetemvagas.webapp.crawler;

import br.com.ondetemvagas.webapp.entity.PortalJob;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;

public class JoinvilleBNE implements Crawler {

  @Override
  public List<PortalJob> findJobs(Document document) {
    return new ArrayList<>();
  }
}

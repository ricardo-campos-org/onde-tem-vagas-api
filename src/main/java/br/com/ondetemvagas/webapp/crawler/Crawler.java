package br.com.ondetemvagas.webapp.crawler;

import br.com.ondetemvagas.webapp.entity.PortalJob;
import java.util.List;
import org.jsoup.nodes.Document;

public interface Crawler {

  List<PortalJob> findJobs(Document document);
}

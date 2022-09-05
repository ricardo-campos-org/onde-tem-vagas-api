package br.com.ondetemvagas.webapp.crawler;

import br.com.ondetemvagas.webapp.entity.PortalJob;
import br.com.ondetemvagas.webapp.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JoinvilleJoinvilleVagas implements Crawler {

  @Override
  public List<PortalJob> findJobs(Document document) {
    List<PortalJob> portalJobList = new ArrayList<>();

    Element olJobListings = document.selectFirst(".job_listings");
    if (olJobListings == null) {
      return portalJobList;
    }

    Elements liJobListing = olJobListings.select(".job_listing");

    for (Element li : liJobListing) {
      PortalJob portalJob = new PortalJob();

      // Nome da vaga e URL
      Element h3JobListingTitle = li.selectFirst(".job_listing-title");
      if (h3JobListingTitle != null) {
        Element a = h3JobListingTitle.selectFirst("a");
        if (a != null) {
          portalJob.setJobTitle(TextUtil.parseJobName(a.text()));
          portalJob.setJobUrl(a.absUrl("href"));
        }
      }

      // Nome da empresa
      Element divJobListingCompany = li.selectFirst(".job_listing-company");
      if (divJobListingCompany != null) {
        portalJob.setCompanyName(divJobListingCompany.text().trim().toLowerCase());
      }

      // Tipo da vaga
      Element divJType = li.selectFirst(".jtype");
      if (divJType != null) {
        portalJob.setJobType(TextUtil.capitalize(divJType.text().trim().toLowerCase()));
      }

      // Descrição
      Elements divDescriptions = li.select(".ti");
      if (divDescriptions.size() >= 2) {
        Element divDescription = divDescriptions.get(1);
        if (divDescription != null) {
          portalJob.setJobDescription(
              TextUtil.capitalize(divDescription.text().trim().toLowerCase()));
        }
      }

      // Data da publicação
      Element divDetails = li.selectFirst(".details");
      if (divDetails != null) {
        Element span = divDetails.selectFirst("span");
        if (span != null) {
          portalJob.setPublishedAt(span.text().trim());
        }
      }

      if (portalJob.isValid()) {
        portalJobList.add(portalJob);
      }
    }

    return portalJobList;
  }
}

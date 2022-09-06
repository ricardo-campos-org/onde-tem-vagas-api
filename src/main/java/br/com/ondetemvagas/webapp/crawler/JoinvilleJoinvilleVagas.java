package br.com.ondetemvagas.webapp.crawler;

import br.com.ondetemvagas.webapp.entity.PortalJob;
import br.com.ondetemvagas.webapp.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JoinvilleJoinvilleVagas implements Crawler {

  @Override
  public List<PortalJob> findJobs(Document document) {
    Elements liJobListing = findJobsElement(document);
    if (Objects.isNull(liJobListing)) {
      return new ArrayList<>();
    }

    List<PortalJob> portalJobList = new ArrayList<>();

    for (Element li : liJobListing) {
      PortalJob portalJob = new PortalJob();

      findTitleAndUrl(portalJob, li);
      findCompany(portalJob, li);
      findType(portalJob, li);
      findDescription(portalJob, li);
      findPublicationDate(portalJob, li);

      if (portalJob.isValid()) {
        portalJobList.add(portalJob);
      }
    }

    return portalJobList;
  }

  private Elements findJobsElement(Document document) {
    Element olJobListings = document.selectFirst(".job_listings");
    if (Objects.isNull(olJobListings)) {
      return null;
    }

    return olJobListings.select(".job_listing");
  }

  private void findTitleAndUrl(PortalJob portalJob, Element root) {
    Element h3JobListingTitle = root.selectFirst(".job_listing-title");
    if (!Objects.isNull(h3JobListingTitle)) {
      Element a = h3JobListingTitle.selectFirst("a");
      if (!Objects.isNull(a)) {
        portalJob.setJobTitle(TextUtil.parseJobName(a.text()));
        portalJob.setJobUrl(a.absUrl("href"));
      }
    }
  }

  private void findCompany(PortalJob portalJob, Element root) {
    Element divJobListingCompany = root.selectFirst(".job_listing-company");
    if (!Objects.isNull(divJobListingCompany)) {
      portalJob.setCompanyName(divJobListingCompany.text().trim().toLowerCase());
    }
  }

  private void findType(PortalJob portalJob, Element root) {
    Element divJType = root.selectFirst(".jtype");
    if (!Objects.isNull(divJType)) {
      portalJob.setJobType(TextUtil.capitalize(divJType.text().trim().toLowerCase()));
    }
  }

  private void findDescription(PortalJob portalJob, Element root) {
    Elements divDescriptions = root.select(".ti");
    if (divDescriptions.size() >= 2) {
      Element divDescription = divDescriptions.get(1);
      if (!Objects.isNull(divDescription)) {
        portalJob.setJobDescription(
            TextUtil.capitalize(divDescription.text().trim().toLowerCase()));
      }
    }
  }

  private void findPublicationDate(PortalJob portalJob, Element root) {
    Element divDetails = root.selectFirst(".details");
    if (!Objects.isNull(divDetails)) {
      Element span = divDetails.selectFirst("span");
      if (!Objects.isNull(span)) {
        portalJob.setPublishedAt(span.text().trim());
      }
    }
  }
}

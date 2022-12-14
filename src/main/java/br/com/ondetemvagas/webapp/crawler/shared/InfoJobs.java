package br.com.ondetemvagas.webapp.crawler.shared;

import br.com.ondetemvagas.webapp.crawler.Crawler;
import br.com.ondetemvagas.webapp.entity.PortalJob;
import br.com.ondetemvagas.webapp.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class InfoJobs implements Crawler {

  @Override
  public List<PortalJob> findJobs(Document document) {
    List<PortalJob> portalJobList = new ArrayList<>();

    Element divMultiple = document.selectFirst("div.multiple");
    if (divMultiple == null) {
      log.warn("div.multiple não localizado no documento!");
      return portalJobList;
    }

    Elements divVagas = divMultiple.select("div.element-vaga");
    log.info("Divs encontradas: {}", divVagas.size());
    for (Element div : divVagas) {
      PortalJob portalJob = new PortalJob();

      // Nome da vaga e URL
      Element divVaga = div.selectFirst("div.vaga");
      if (divVaga != null) {
        Element a = divVaga.selectFirst("a");
        if (a != null) {
          portalJob.setJobTitle(TextUtil.capitalize(a.attr("title").toLowerCase()));
          portalJob.setJobUrl(a.absUrl("href"));
        }
      }

      // Nome da empresa
      Element divContainerVaga = div.selectFirst("div.container-vaga");
      if (divContainerVaga != null) {
        Element divComp = divContainerVaga.selectFirst("div.vaga-company");
        if (divComp != null) {
          Element a = divComp.selectFirst("a");
          if (a != null) {
            portalJob.setCompanyName(TextUtil.capitalize(a.text().trim().toLowerCase()));
          }
        }
      }

      // Tipo da vaga
      // Não tem no site

      // Descrição
      if (divContainerVaga != null) {
        Element divVagaDesc = divContainerVaga.selectFirst("div.vagaDesc");
        if (divVagaDesc != null) {
          portalJob.setJobDescription(
              TextUtil.capitalize(divVagaDesc.text().trim().toLowerCase()));
        }
      }

      // Data da publicação
      if (divContainerVaga != null) {
        Elements divs = divContainerVaga.select("div");
        for (Element divCVaga : divs) {
          Element p = divCVaga.selectFirst("p.location2");
          if (p == null) {
            continue;
          }

          Element spanData = p.selectFirst("span.data");
          if (spanData != null) {
            portalJob.setPublishedAt(spanData.text().trim());
          }
        }
      }

      if (portalJob.isValid()) {
        if (!Objects.isNull(portalJob.getCompanyName())) {
          portalJob.setCompanyName("");
        }

        portalJobList.add(portalJob);
      }
    }

    return portalJobList;
  }
}

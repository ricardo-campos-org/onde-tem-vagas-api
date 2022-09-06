package br.com.ondetemvagas.webapp.util;

import br.com.ondetemvagas.webapp.entity.PortalJob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortalJobUtilTest {

  @Test
  void listToMapByUrlTest() {
    List<PortalJob> list = createList(7);

    Map<String, PortalJob> map = PortalJobUtil.listToMapByUrl(list);

    Assertions.assertFalse(map.isEmpty());
    Assertions.assertEquals(7, map.size());
  }

  private List<PortalJob> createList(int size) {
    final List<PortalJob> list = new ArrayList<>(size);
    final String jobUrlTemplate = "http://job-url-here%d.com";
    final String jobTitleTemplate = "Developer%d";

    for (int i = 1; i <= size; i++) {
      list.add(
          PortalJob.builder()
              .id((long) i)
              .jobUrl(String.format(jobUrlTemplate, i))
              .jobTitle(String.format(jobTitleTemplate, i))
              .build());
    }

    return list;
  }
}

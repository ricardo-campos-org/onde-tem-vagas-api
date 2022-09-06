package br.com.ondetemvagas.webapp.util;

import br.com.ondetemvagas.webapp.entity.PortalJob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class contains methods for handling portal jobs utilities. */
public class PortalJobUtil {

  /**
   * Convert a list of portal jobs to a map. The map key is the job url.
   *
   * @param list the job list to be converted.
   * @return a map containing all items of the given list.
   */
  public static Map<String, PortalJob> listToMapByUrl(List<PortalJob> list) {
    Map<String, PortalJob> portalJobMap = new HashMap<>();
    list.forEach((portalJob) -> portalJobMap.put(portalJob.getJobUrl(), portalJob));
    return portalJobMap;
  }
}

package br.com.ondetemvagas.webapp.crawler;

public class CrawlerFactory {

  public static Crawler createInstance(String className) {
    // Joinville
    if (className.equals("JoinvilleBNE")) {
      return new JoinvilleBNE();
    }
    if (className.equals("JoinvilleIndeed")) {
      return new JoinvilleIndeed();
    }
    if (className.equals("JoinvilleInfoJobs")) {
      return new JoinvilleInfoJobs();
    }
    if (className.equals("JoinvilleJoinvilleVagas")) {
      return new JoinvilleJoinvilleVagas();
    }
    if (className.equals("JoinvilleTrabalhaBrasil")) {
      return new JoinvilleTrabalhaBrasil();
    }

    return null;
  }
}

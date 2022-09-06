package br.com.ondetemvagas.webapp.repository;

import br.com.ondetemvagas.webapp.entity.CrawlerLog;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CrawlerLogRepository extends JpaRepository<CrawlerLog, Integer> {

  @Query(
      value = "SELECT * FROM crawler_log WHERE created_at >= ? ORDER BY created_at, id",
      nativeQuery = true)
  List<CrawlerLog> findAllByGreaterDateTime(LocalDateTime localDateTime);
}

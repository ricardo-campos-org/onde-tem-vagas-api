package br.com.ondetemvagas.webapp.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "crawler_log")
public class CrawlerLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "portal_id")
  private Long portalId;

  @Column private String text;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Override
  public String toString() {
    String template = "Crawler_log={id=%d, portalId=%d, text='%s', createdAt='%s'}";
    return String.format(template, id, portalId, text, createdAt);
  }
}

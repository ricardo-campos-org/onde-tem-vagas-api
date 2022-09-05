package br.com.ondetemvagas.webapp.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "portal_job")
public class PortalJob {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 600)
  @Column(name = "job_title")
  private String jobTitle;

  @Size(max = 600)
  @Column(name = "company_name")
  private String companyName;

  @Size(max = 30)
  @Column(name = "job_type")
  private String jobType;

  @Size(max = 600)
  @Column(name = "job_description")
  private String jobDescription;

  @Size(max = 30)
  @Column(name = "published_at")
  private String publishedAt;

  @Size(max = 1000)
  @Column(name = "job_url")
  private String jobUrl;

  @Column(name = "portal_id")
  private Long portalId;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortalJob portalJob = (PortalJob) o;
    return id.equals(portalJob.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    String template =
        "PortalJob{id=%d, jobTitle='%s', companyName='%s', jobType='%s', "
            + "jobDescription='%s', publishedAt='%s', jobUrl='%s', portalId=%d, createdAt='%s'}";
    return String.format(
        template,
        id,
        jobTitle,
        companyName,
        jobType,
        jobDescription,
        publishedAt,
        jobUrl,
        portalId,
        createdAt);
  }

  public boolean isValid() {
    return !Objects.isNull(jobTitle)
        && !jobTitle.trim().isEmpty()
        && !Objects.isNull(jobUrl)
        && !jobUrl.trim().isEmpty();
  }
}

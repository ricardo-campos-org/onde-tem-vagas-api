package br.com.ondetemvagas.webapp.repository;

import br.com.ondetemvagas.webapp.entity.UserJob;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJobRepository extends JpaRepository<UserJob, Long> {

  @Query(value = "select * from user_job where saved_at >= ?", nativeQuery = true)
  List<UserJob> findAllBySavedStartingAt(LocalDateTime startingAt);
}

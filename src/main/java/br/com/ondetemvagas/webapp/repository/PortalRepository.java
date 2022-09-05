package br.com.ondetemvagas.webapp.repository;

import br.com.ondetemvagas.webapp.entity.Portal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortalRepository extends JpaRepository<Portal, Long> {

  List<Portal> findAll();

  List<Portal> findAllByEnabled(Boolean enabled);

  List<Portal> findAllByCityId(Long city_id);
}

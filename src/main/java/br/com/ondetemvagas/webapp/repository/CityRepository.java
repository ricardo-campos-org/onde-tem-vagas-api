package br.com.ondetemvagas.webapp.repository;

import br.com.ondetemvagas.webapp.entity.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

  List<City> findAllByStateId(Long stateId);
}

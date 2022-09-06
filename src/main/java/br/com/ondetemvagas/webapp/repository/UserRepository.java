package br.com.ondetemvagas.webapp.repository;

import br.com.ondetemvagas.webapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  List<User> findAllByEnabled(Boolean enabled);
}

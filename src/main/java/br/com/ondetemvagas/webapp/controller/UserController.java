package br.com.ondetemvagas.webapp.controller;

import br.com.ondetemvagas.webapp.entity.User;
import br.com.ondetemvagas.webapp.exception.UserAlreadyExistsException;
import br.com.ondetemvagas.webapp.exception.UserNotFoundException;
import br.com.ondetemvagas.webapp.repository.UserRepository;
import br.com.ondetemvagas.webapp.request.UserRequest;
import br.com.ondetemvagas.webapp.request.UserUpdateRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** This class exposes endpoint related to the user. */
@NoArgsConstructor
@Setter
@RestController
@RequestMapping("/users")
public class UserController {

  private UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public User create(@Valid @RequestBody UserRequest userRequest) {
    Optional<User> userOp = userRepository.findByEmail(userRequest.getEmail());
    if (userOp.isPresent()) {
      throw new UserAlreadyExistsException();
    }

    User user =
        User.builder()
            .firstName(userRequest.getFirstName())
            .lastName(userRequest.getLastName())
            .email(userRequest.getEmail())
            .enabled(Boolean.TRUE)
            .updatedAt(LocalDateTime.now())
            .build();

    return userRepository.save(user);
  }

  @GetMapping(value = "/find-by-email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> findByEmail(@Email @RequestParam String email) {
    Optional<User> userOp = userRepository.findByEmail(email);
    if (userOp.isEmpty()) {
      return new ArrayList<>();
    }

    return Collections.singletonList(userOp.get());
  }

  @GetMapping(value = "/find-enabled", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> findAll() {
    return userRepository.findAllByEnabled(Boolean.TRUE);
  }

  @GetMapping(value = "/find-disabled", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> findDisabled() {
    return userRepository.findAllByEnabled(Boolean.FALSE);
  }

  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public User update(@Valid @RequestBody UserUpdateRequest userRequest) {
    Optional<User> userOp = userRepository.findById(userRequest.getId());
    if (userOp.isEmpty()) {
      throw new UserNotFoundException();
    }

    User userToUpdate = userOp.get();

    if (!Objects.isNull(userRequest.getFirstName())) {
      userToUpdate = userToUpdate.withFirstName(userRequest.getFirstName());
    }
    if (!Objects.isNull(userRequest.getLastName())) {
      userToUpdate = userToUpdate.withLastName(userRequest.getLastName());
    }
    if (!Objects.isNull(userRequest.getEmail())) {
      userToUpdate = userToUpdate.withEmail(userRequest.getEmail());
    }

    userToUpdate = userToUpdate
          .withUpdatedAt(LocalDateTime.now());

    return userRepository.save(userToUpdate);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteUser(@PathVariable Long id) {
    Optional<User> userOp = userRepository.findById(id);
    if (userOp.isEmpty()) {
      throw new UserNotFoundException();
    }

    User userToUpdate = userOp.get().withEnabled(Boolean.FALSE);

    userRepository.save(userToUpdate);
  }
}

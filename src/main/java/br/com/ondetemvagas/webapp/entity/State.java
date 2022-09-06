package br.com.ondetemvagas.webapp.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "state")
public class State {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String name;

  @Column private String acronym;

  @OneToMany(mappedBy = "state")
  private Set<City> cities;

  public State() {
    this(0L, "", "", new HashSet<>());
  }

  public State(Long id, String name, String acronym, Set<City> cities) {
    this.id = id;
    this.name = name;
    this.acronym = acronym;
    this.cities = cities;
  }

  @Override
  public String toString() {
    return "State{" + "id=" + id + ", name='" + name + '\'' + ", acronym='" + acronym + '\'' + '}';
  }
}

package de.maikfigura;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@EqualsAndHashCode(callSuper = false)
@ToString
public class Person extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public UUID id;

  @Column(unique = true)
  public String name;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Trace> traces = new HashSet<Trace>();

  public static Optional<Person> findByName(String name) {
    return find("name", name).firstResultOptional();
  }

}

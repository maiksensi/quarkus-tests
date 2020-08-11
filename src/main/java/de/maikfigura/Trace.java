package de.maikfigura;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@EqualsAndHashCode(callSuper = false)
@ToString
public class Trace extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public UUID id;
  @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
  public Person traceOwner;
  @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST,
      CascadeType.DETACH }, fetch = FetchType.LAZY)
  public List<Person> additionalParticipants = new ArrayList<Person>();
  public String place;
  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  public ZonedDateTime startTime;
  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  public ZonedDateTime stopTime;
  public String comment;
}

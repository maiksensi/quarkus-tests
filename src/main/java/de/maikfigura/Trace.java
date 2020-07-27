package de.maikfigura;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

// @Entity
// public class Trace extends PanacheEntity {
//   @ManyToOne
//   public Person traceOwner;
//   @ElementCollection
//   public List<String> participants;
//   public String place;
//   @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
//   public ZonedDateTime startTime;
//   @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
//   public ZonedDateTime stopTime;
//   public String comment;

//   public String toString() {
//     return String.format("%s, [%s, %s], %s, %s, %s", this.traceOwner, this.participants.get(0),
//         this.participants.get(1), this.place, this.startTime, this.stopTime, this.comment);
//   }
// }

public class Trace extends PanacheMongoEntityBase {
  public ObjectId id;
  public Person traceOwner;
  public List<Person> participants;
  public ZonedDateTime startTime;
  public ZonedDateTime stopTime;
  public String comment;

}
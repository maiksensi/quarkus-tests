package de.maikfigura;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

// @Entity
// public class Person extends PanacheEntity {
//   public String name;
// }

public class Person {
  public String name;

  public Person() {
  }

  public Person(String name) {
    this.name = name;
  }

  public static List<Person> from(String participants) {
    String[] splitedParticipants = participants.split(",");
    List<Person> listOfPersons = new ArrayList<Person>();

    for (String participant : splitedParticipants) {
      listOfPersons.add(new Person(participant));
    }

    return listOfPersons;
  }

}
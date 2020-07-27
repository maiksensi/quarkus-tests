package de.maikfigura;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
@Transactional
public class Main {

  void onStart(@Observes StartupEvent startupEvent) {
    // Trace.persist(new Trace(new String("Maik"), new String[] { new
    // String("Anna"), new String("Benjamin") },
    // "Stuttgart", ZonedDateTime.now(), ZonedDateTime.now().plusHours(1),
    // "Essen"));
  }
}

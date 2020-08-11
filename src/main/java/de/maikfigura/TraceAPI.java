package de.maikfigura;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

@Path("/trace")
public class TraceAPI {
  private static final Logger LOG = LoggerFactory.getLogger(TraceAPI.class);

  @Inject
  UserTransaction transaction;

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response getOne(@PathParam UUID id) {
    final Optional<Trace> trace = Trace.findByIdOptional(id);
    if (trace.isPresent()) {
      return Response.ok(JsonbBuilder.create().toJson(trace)).build();
    } else {
      return Response.status(HttpStatus.SC_NO_CONTENT).build();
    }
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response createTrace(@Valid Trace trace) {
    List<Person> persistedParticipants = new ArrayList<Person>();
    for (Person participant : trace.additionalParticipants) {
      // persist all participants not yet registered
      if (Person.findByName(participant.name).isEmpty()) {
        participant.persistAndFlush();
        Person persistedPerson = Person.findByName(participant.name).get();
        persistedParticipants.add(persistedPerson);
      } else {
        persistedParticipants.add(Person.findByName(participant.name).get());
      }
    }

    // write back all persisted participants to the entity list
    trace.additionalParticipants = persistedParticipants;

    Optional<Person> traceOwner = Person.find("name", trace.traceOwner.name).firstResultOptional();
    // persist all traceOwners not yet known
    if (traceOwner.isEmpty()) {
      trace.traceOwner.persist();
    } else {
      trace.traceOwner = traceOwner.get();
    }
    trace.persist();
    trace.flush();
    return Response.ok(trace).build();
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response updateTrace(@Valid final Trace trace, @PathParam UUID id)
      throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException,
      HeuristicMixedException, HeuristicRollbackException {
    Trace tracy = Trace.findById(id);
    List<Person> persistedParticipants = new ArrayList<Person>();
    for (Person participant : trace.additionalParticipants) {
      // persist all participants not yet registered
      if (participant.id != null) {
        if (Person.findByIdOptional(participant.id).isEmpty()) {
          Person p = new Person();
          p.name = participant.name;
          p.persistAndFlush();
          Person persistedPerson = (Person) Person.findByIdOptional(participant.id).get();
          persistedParticipants.add(persistedPerson);
        } else {
          Person pp = (Person) Person.findByIdOptional(participant.id).get();
          pp.name = participant.name;
          persistedParticipants.add(pp);
        }
      } else {
        Person p = new Person();
        p.name = participant.name;
        p.persistAndFlush();
        Person persistedPerson = Person.findByName(participant.name).get();
        persistedParticipants.add(persistedPerson);
      }
    }

    tracy.additionalParticipants = persistedParticipants;

    tracy.traceOwner = trace.traceOwner;
    tracy.comment = trace.comment;
    tracy.place = trace.place;
    tracy.startTime = trace.startTime;
    tracy.stopTime = trace.stopTime;
    // tracy.persistAndFlush();
    // transaction.commit();

    Trace tracinger = Trace.findById(id);
    return Response.ok(tracinger).build();

    // Set<Person> persistedParticipants = new HashSet<Person>();
    // for (Person participant : trace.additionalParticipants) {
    // // persist all participants not yet registered
    // if (Person.findByName(participant.name).isEmpty()) {
    // participant.persistAndFlush();
    // Person persistedPerson = Person.findByName(participant.name).get();
    // persistedParticipants.add(persistedPerson);
    // } else {
    // persistedParticipants.add(Person.findByName(participant.name).get());
    // }
    // }

    // // write back all persisted participants to the entity list
    // trace.additionalParticipants = persistedParticipants;

    // Optional<Person> traceOwner = Person.find("name",
    // trace.traceOwner.name).firstResultOptional();
    // if (traceOwner.isEmpty()) {
    // trace.traceOwner.persist();
    // } else {
    // trace.traceOwner = traceOwner.get();
    // }

    // trace.persistAndFlush();

    // if (traceOwner.isPresent()) {
    // entity.traceOwner = traceOwner.get();
    // entity.flush();
    // } else {
    // Person newTraceOwner = new Person();
    // newTraceOwner.name = trace.traceOwner.name;
    // newTraceOwner.persist();
    // entity.traceOwner = newTraceOwner;
    // entity.flush();
    // }

  }
}

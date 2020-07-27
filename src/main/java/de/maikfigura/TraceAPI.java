package de.maikfigura;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

@Path("/trace")
public class TraceAPI {
  private static final Logger LOG = LoggerFactory.getLogger(TraceAPI.class);

  // @Inject
  // UserTransaction transaction;

  // @GET
  // @Path("/{id}")
  // @Produces(MediaType.APPLICATION_JSON)
  // @Transactional
  // public Response getOne(@PathParam final Long id) {
  // Optional<Trace> trace = Trace.findByIdOptional(id);
  // if (trace.isPresent()) {
  // return Response.ok(JsonbBuilder.create().toJson(trace)).build();
  // } else {
  // return Response.status(HttpStatus.SC_NO_CONTENT).build();
  // }
  // }

  // @POST
  // @Produces(MediaType.APPLICATION_JSON)
  // @Consumes(MediaType.APPLICATION_JSON)
  // public Response createTrace(@Valid Trace trace) {
  // try {
  // LOG.info(trace);
  // transaction.begin();
  // trace.persist();
  // transaction.commit();
  // } catch (NotSupportedException | SystemException | SecurityException |
  // IllegalStateException | RollbackException
  // | HeuristicMixedException | HeuristicRollbackException e1) {
  // LOG.error("Could not finish transaction to save entity", e1);
  // return Response.status(HttpStatus.SC_BAD_REQUEST).build();

  // }
  // return Response.ok(Trace.find("startTime =?1 AND traceOwner = ?2",
  // trace.startTime, trace.traceOwner).firstResult())
  // .build();
  // }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createTrace(Trace trace) {
    trace.persistOrUpdate();
    return Response.ok(Trace.find("startTime =?1 AND traceOwner = ?2", trace.startTime, trace.traceOwner).firstResult())
        .build();
  }
}
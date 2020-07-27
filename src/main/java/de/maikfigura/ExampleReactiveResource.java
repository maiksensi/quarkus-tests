package de.maikfigura;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;

@Path("/helloreactive")
public class ExampleReactiveResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Uni<String> helloreactive() throws InterruptedException {
    // is returned immediately and non blocking
    return Uni.createFrom().item("hello");
  }
}

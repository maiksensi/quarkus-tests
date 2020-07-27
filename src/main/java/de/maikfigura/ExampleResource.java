package de.maikfigura;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

@Path("/hello")
public class ExampleResource {
  private static final Logger LOG = LoggerFactory.getLogger(ExampleResource.class);

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() throws InterruptedException {
    LOG.info("Example hello");
    return "hello";
  }
}

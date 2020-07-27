package de.maikfigura;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleReactiveResourceTest {

  @Test
  public void testHelloReactiveEndpoint() {
    given().when().get("/helloreactive").then().statusCode(200).body(is("hello"));
  }
}

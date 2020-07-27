package de.maikfigura;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TraceAPIText {
  public void testSaveTrace() {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("traceOwner", "Maik");
    params.put("participants", "[\"Anna\", \"Benjamin\"]");
    params.put("place", "70565, Stuttgart, Industriestraße 25");
    params.put("startTime", "2020-12-03T10:15:30 Europe/Berlin");
    params.put("stopTime", "2020-12-03T11:30:30 Europe/Berlin");
    params.put("comment", "Brunch mit Anna und Benjamin");
    given().params(params).post("/trace").andReturn();
  }

}
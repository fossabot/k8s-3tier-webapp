package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class AKafkaResourceTest {

	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	void testGet() {
		given()
				.when()
				.get("/quarkus/kafka/get")
				.then()
				.statusCode(200)
				.body(containsString(""));
	}

	@Test
	void testPublish() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/kafka/publish")
				.then()
				.statusCode(200)
				.body(containsString(respbody));
	}
}

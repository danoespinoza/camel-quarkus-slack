package cl.danoespinoza.route;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.SlackResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@QuarkusTest
public class SlackRouteTest {

	@Test
	@DisplayName("Test Health Service")
	public void testHealth() {
		Response response = given()
				.when().get("/slack/health");
		
		SlackResponse slackResponse = response.then().contentType(ContentType.JSON).statusCode(200).extract().as(SlackResponse.class);
		
		assertThat(slackResponse.getCode(), equalTo(200));
	}

//	@Test
//	@DisplayName("Test Send to Slack")
//	public void testSendToSlack() {
//		String body = "{\n"
//				+ "    \"message\": \"Test on build in Quarkus\"\n"
//				+ "}";
//				
//		Response response = given()
//				.contentType(ContentType.JSON)
//				.body(body)
//				.when().post("/slack/send");
//		
//		SlackResponse slackResponse = response.then().contentType(ContentType.JSON).statusCode(200).extract().as(SlackResponse.class);
//		
//		assertThat(slackResponse.getCode(), equalTo(200));
//	}
}

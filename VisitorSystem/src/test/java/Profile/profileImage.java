package Profile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;
public class profileImage {
	String AuthToken;
	@BeforeClass
	public void getToken() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		//get the signOn key
		Response keyResponse = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-Device-Id", "3efe6bbeb55f4411")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.when()
				.get("/key")
				.then()
				.statusCode(200)
				.extract().response();

		String secretKey = keyResponse.jsonPath().getString("signOnKey");
		assertNotNull(secretKey, "Secret key is null!");

		//authenticate before for the auth token
		ObjectMapper objectMapper = new ObjectMapper();
		String email = "vivek@moco.com.np";
		String requestDeviceId = "3efe6bbeb55f4411";
		Map<String, Object> credentials = new HashMap<>();
		credentials.put("email", email);
		credentials.put("pin", "123426");

		Map<String, Object> jsonBody = new HashMap<>();
		jsonBody.put("credentials", credentials);

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

		jsonBody.put("signature", requestSignature);

		// Send request
		Response Authresponse = given()
				.header("X-GEO-Location", "12,12")
				.header("X-Device-Id", requestDeviceId)
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("authenticate")
				.then()
				.statusCode(200)
				.log().all()
				.extract().response();
		AuthToken = Authresponse.getHeader("X-AUTH-TOKEN");

}

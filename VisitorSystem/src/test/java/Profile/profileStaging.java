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

public class profileStaging {
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
	
	@Test
	public void getProfileInformation() {
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(200)
	            .extract().response();
		
		String signature = response.jsonPath().getString("signature");
		String fullName = response.jsonPath().getString("fullName");
		String country = response.jsonPath().getString("country");
		String documentNumber = response.jsonPath().getString("documentNumber");
		String dateOfBirth = response.jsonPath().getString("dateOfBirth");
		String documentExpiryDate = response.jsonPath().getString("documentExpiryDate");
		String gender = response.jsonPath().getString("gender");
		String documentType = response.jsonPath().getString("documentType");
		String status = response.jsonPath().getString("profile.status");
        String verificationStatus = response.jsonPath().getString("profile.verificationStatus");
        String loginAttemptCount = response.jsonPath().getString("profile.loginAttemptCount");
        String isBiometric = response.jsonPath().getString("profile.isBiometric");
		

		assertNotNull(signature, "signature is missing");
		assertNotNull(fullName, "fullname is missing");
		assertNotNull(country, "country is missing");
		assertNotNull(signature,"signature is missing");
		assertNotNull(documentNumber, "documentNumber is missing");
        assertNotNull(documentType, "documentType is missing");
        assertNotNull(dateOfBirth, "dateOfBirth is missing");
        assertNotNull(documentExpiryDate, "documentExpiryDate is missing");
        assertNotNull(gender, "gender is missing");
        assertNotNull(status, "status is missing");
        assertNotNull(verificationStatus, "verificationStatus is missing");
        assertNotNull(loginAttemptCount, "loginAttemptCount is missing");
        assertNotNull(isBiometric, "isBiometric is missing");

        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(fullName.isEmpty(), "fullname is missing");
        assertFalse(country.isEmpty(), "country is missing");
        assertFalse(documentNumber.isEmpty(), "documentNumber is missing");
        assertFalse(documentType.isEmpty(), "documentType is missing");
        assertFalse(dateOfBirth.isEmpty(), "dateOfBirth is missing");
        assertFalse(documentExpiryDate.isEmpty(), "documentExpiryDate is missing");
        assertFalse(gender.isEmpty(), "gender is missing");
        assertFalse(status.isEmpty(), "status is missing");
        assertFalse(verificationStatus.isEmpty(), "verificationStatus is missing");
        assertFalse(loginAttemptCount.isEmpty(), "loginAttemptCount is missing");
        assertFalse(isBiometric.isEmpty(), "isBiometric is missing");

        //check if the device id is same in request and response
        assertEquals("John Smith",fullName);
        assertEquals("India",country);
        
      //check if the token is in the present in the response
        assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
        System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
		
	}
	@Test
	public void getProfileInformationwithoutDevice() {
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(400)
	            .extract().response();
		
		// Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
		
	}
    @Test
	public void getProfileInformationwithoutGeo() {
    	Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(400)
	            .extract().response();
		
		// Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
		
	}
	@Test
	public void getProfileInformationwithoutUserAgent() {
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(400)
	            .extract().response();
		
		// Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
		
	}
	@Test
	public void getProfileInformationwithoutAuth() {
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(400)
	            .extract().response();
		
		// Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
		
	}
	@Test
	public void getProfileInformationwithInvalidDevice() {
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(422)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid data.");
	}
	@Test
	public void getProfileInformationwithInvalidLocation() {
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(422)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid data.");
		
	}
	@Test
	public void getProfileInformationwithInvalidAuth() {
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(401)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_AUTHENTICATION_FAIL");
		 assertEquals(description,"Authentication Failed.");
		
	}
	@Test
	public void getProfileInformationwithServerDown() {
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(401)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_ERR");
		 assertEquals(description,"Internal Server Error");
		
		
	}
}   


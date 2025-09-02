package Profile;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

//import java.util.*;

public class getProfileDetails {
	String AuthToken;
	String requestDeviceId = "visitor-app-device"; 
	String input_email = "vivek@moco.com.np";
	String input_pin = "147369";
	@BeforeClass
	public void getToken() throws Exception{
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response1 = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .when()
                .get("/key")
            .then()
                .statusCode(200)
                .extract().response();
        		response1.prettyPrint();

       String secretKey1 = response1.jsonPath().getString("signOnKey");
        //assertNotNull(secretKey, "Secret key is null!");
       	
        
        ObjectMapper objectMapper = new ObjectMapper();
        String email = input_email;
        String plain_pin = input_pin;
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey1);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey1);
        
        jsonBody.put("signature", requestSignature);
        
        
     // Send request
        Response response2 = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        AuthToken = response2.getHeader("X-AUTH-TOKEN");
	        //String secretKey = response2.jsonPath().getString("sessionKey");
	}
	
	@Test
	public void getProfileInformation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(200)
	            .extract().response();
		response.prettyPrint();
		
		String signature = response.jsonPath().getString("signature");
		String fullName = response.jsonPath().getString("fullName");
		String country = response.jsonPath().getString("country");
		String documentNumber = response.jsonPath().getString("documentNumber");
		String dateOfBirth = response.jsonPath().getString("dateOfBirth");
		String documentExpiryDate = response.jsonPath().getString("documentExpiryDate");
		String gender = response.jsonPath().getString("gender");
		String documentType = response.jsonPath().getString("documentType");
		String status = response.jsonPath().getString("status");
        String verificationStatus = response.jsonPath().getString("verificationStatus");
        String loginAttemptCountPin = response.jsonPath().getString("loginAttemptCountPin");
        String loginAttemptCountBiometric = response.jsonPath().getString("loginAttemptCountBiometric");
        
		

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
        assertNotNull(loginAttemptCountPin, "loginAttemptCount is missing");
        assertNotNull(loginAttemptCountBiometric, "isBiometric is missing");

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
        assertFalse(loginAttemptCountPin.isEmpty(), "loginAttemptCount is missing");
        assertFalse(loginAttemptCountBiometric.isEmpty(), "isBiometric is missing");
        
        System.out.println(status);
      //check if the token is in the present in the response
        assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
        System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
		
	}
	@Test
	public void getProfileInformationwithoutDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
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
    	baseURI = "https://visitor0.moco.com.np/visitor";
    	Response response = given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", "travel-app-phone")
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
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "")
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
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN","")
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
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone#$")
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
	    // String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	    // assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     //assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid device Id found.");
	}
	@Test
	public void getProfileInformationwithInvalidLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12AA12")
	            .header("X-Device-Id", "travel-app-phone")
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
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	    // assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     //assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid Geo location found.");
		
	}
	@Test
	public void getProfileInformationwithInvalidAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN","()(jd")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(401)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	    // String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     //assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     //assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_AUTHENTICATION_FAIL");
		 assertEquals(description,"Authentication Failed.");
		
	}
	@Test
	public void getProfileInformationwithServerDown() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(500)
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
//	@AfterClass
//	public void logout() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//	      Response response = given()
//	          .header("X-GEO-Location", "12,12")
//	          .header("X-AUTH-TOKEN",AuthToken)
//	          .header("X-Device-Id", requestDeviceId)
//	          .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	      .when()
//	          .delete("/authenticate");
//	      response.then().statusCode(200);
//		
//	}
}   


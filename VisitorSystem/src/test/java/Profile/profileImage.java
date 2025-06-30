package Profile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
//import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class profileImage {
	String AuthToken;
	String requestDeviceId = "visitor-app-device"; 
	String input_email = "vivek@moco.com.np";
	String input_pin = "123654";
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
	public void getProfileImage() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
	        .then()
	            .statusCode(200)
	            .extract().response();
		//check if the token is in the present in the response
        assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
        System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
	}
	
	@Test
	public void getDocumentImage() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "document")
	        .when()
	            .get("/profile/image/{type}")
	        .then()
	            .statusCode(200)
	            .extract().response();
		//check if the token is in the present in the response
        assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
        System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
		
	}
	
	@Test
	public void getProfileImagewithoutDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
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
	public void getProfileImagewithoutGeo() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
    	Response response = given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", requestDeviceId)
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
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
	public void getProfileImagewithoutUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
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
	public void getProfileImagewithoutAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
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
	public void getProfileImagewithInvalidDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "AS##")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
	        .then()
	            .statusCode(422)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid device Id found.");
	}
	
	@Test
	public void getProfileImagewithInvalidLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12AA,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
	        .then()
	            .statusCode(422)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	    
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	    
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid Geo location found.");
		
	}
	@Test
	public void getProfileImagewithInvalidAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("X-AUTH-TOKEN","SS")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
	        .then()
	            .statusCode(401)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	    
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	    
	     
	     assertEquals(code,"GNR_AUTHENTICATION_FAIL");
		 assertEquals(description,"Authentication Failed.");
		
	}
	@Test
	public void getProfileImagewithServerDown() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "portrait")
	        .when()
	            .get("/profile/image/{type}")
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
	
	@Test
	public void getProfileImagewithoutparam() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "")
	        .when()
	            .get("/profile/image/{type}")
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
	public void getProfileImagewithoutImagepushed() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "moco-dev-app")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .pathParam("type", "potrait")
	        .when()
	            .get("/profile/image/{type}")
	        .then()
	            .statusCode(404)
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
	     
	     assertEquals(code,"GNR_NOT_FOUND");
	     assertEquals(description,"Visitor Image not found.");
		
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
//	}
		
	}

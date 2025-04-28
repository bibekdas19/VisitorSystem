package Profile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class profile {
	String AuthToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ2aXZla0Btb2NvLmNvbS5ucCIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6IjNlZmU2YmJlYjU1ZjQ0MTEiLCJpYXQiOjE3NDU4MzMxNDAsImV4cCI6MTc0NTgzNDA0MH0.fammc6LB9jdsagm0MjajdEv4if50uy42k-XoHLo9tbkJouJ6J-XoGJZr9_5NA5q9";
	String secretKey = "oOx4g8VJrs3xOCgjyu8f8n3d+V1FsIMODA4Z0JqHaU4=";
//    @BeforeClass
//    public void getToken() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		//get the signOn key
//		Response keyResponse = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", "3efe6bbeb55f4411")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.when()
//				.get("/key")
//				.then()
//				.statusCode(200)
//				.extract().response();
//
//		secretKey = keyResponse.jsonPath().getString("signOnKey");
//		assertNotNull(secretKey, "Secret key is null!");
//
//		//authenticate before for the auth token
//		ObjectMapper objectMapper = new ObjectMapper();
//		String email = "vivek@moco.com.np";
//		String requestDeviceId = "3efe6bbeb55f4411";
//		Map<String, Object> credentials = new HashMap<>();
//		credentials.put("email", email);
//		credentials.put("pin", "123426");
//
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("credentials", credentials);
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//		jsonBody.put("signature", requestSignature);
//
//		// Send request
//		Response Authresponse = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/authenticate")
//				.then()
//				.statusCode(200)
//				.log().all()
//				.extract().response();
//		AuthToken = Authresponse.getHeader("X-AUTH-TOKEN");
//		
//}
    @Test
    public void getUserDetails() throws Exception {
		String requestDeviceId = "3efe6bbeb55f4411";
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new HashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2030-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		System.out.println(jsonBody);
		
		Response response = given()
				.header("X-GEO-Location", "12,12")
				.header("X-Device-Id", requestDeviceId)
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.header("X-AUTH-TOKEN",AuthToken)
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("/profile")
				.then()
				.statusCode(200)
				.log().all()
				.extract().response();
		
		 String signature = response.jsonPath().getString("signature");
	     String deviceId = response.jsonPath().getString("deviceId");
	     String fullName = response.jsonPath().getString("profile.fullName");
	     String country = response.jsonPath().getString("profile.country");
	     String documentNumber = response.jsonPath().getString("profile.documentNumber");
	     String documentType = response.jsonPath().getString("profile.documentType");
	     String dateOfBirth = response.jsonPath().getString("profile.dateOfBirth");
	     String documentExpiryDate = response.jsonPath().getString("profile.documentExpiryDate");
	     String responseemail = response.jsonPath().getString("profile.email");
	     String gender = response.jsonPath().getString("profile.gender");
	     String status = response.jsonPath().getString("profile.status");
	     String verificationStatus = response.jsonPath().getString("profile.verificationStatus");
	     String loginAttemptCount = response.jsonPath().getString("profile.loginAttemptCount");
	     String isBiometric = response.jsonPath().getString("profile.isBiometric");
		
	   //check if response includes null value
	        assertNotNull(signature, "signature is missing");
	        assertNotNull(deviceId, "device is missing");
	        assertNotNull(fullName, "fullname is missing");
	        assertNotNull(country, "country is missing");
	        assertNotNull(documentNumber, "documentNumber is missing");
	        assertNotNull(documentType, "documentType is missing");
	        assertNotNull(dateOfBirth, "dateOfBirth is missing");
	        assertNotNull(documentExpiryDate, "documentExpiryDate is missing");
	        assertNotNull(responseemail, "responseemail is missing");
	        assertNotNull(gender, "gender is missing");
	        assertNotNull(status, "status is missing");
	        assertNotNull(verificationStatus, "verificationStatus is missing");
	        assertNotNull(loginAttemptCount, "loginAttemptCount is missing");
	        assertNotNull(isBiometric, "isBiometric is missing");
	        
	        //check is response includes empty values
	        assertFalse(signature.isEmpty(), "Signature is empty in the response");
	        assertFalse(deviceId.isEmpty(), "device is missing");
	        assertFalse(fullName.isEmpty(), "fullname is missing");
	        assertFalse(country.isEmpty(), "country is missing");
	        assertFalse(documentNumber.isEmpty(), "documentNumber is missing");
	        assertFalse(documentType.isEmpty(), "documentType is missing");
	        assertFalse(dateOfBirth.isEmpty(), "dateOfBirth is missing");
	        assertFalse(documentExpiryDate.isEmpty(), "documentExpiryDate is missing");
	        assertFalse(responseemail.isEmpty(), "responseemail is missing");
	        assertFalse(gender.isEmpty(), "gender is missing");
	        assertFalse(status.isEmpty(), "status is missing");
	        assertFalse(verificationStatus.isEmpty(), "verificationStatus is missing");
	        assertFalse(loginAttemptCount.isEmpty(), "loginAttemptCount is missing");
	        assertFalse(isBiometric.isEmpty(), "isBiometric is missing");
	        
	        //check if the device id is same in request and response
	        
//	        assertEquals(requestDeviceId,deviceId);
//	        assertEquals("John Smith",fullName);
//	        assertEquals("India",country);
	        
    	
    	
    }
    
//    @Test
//    public void getUserDetailswithoutGeo() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "SINGH SAGAR");
//		jsonBody.put("country", "IND");
//		jsonBody.put("documentNumber", "R9079271");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2028-01-23");
//		jsonBody.put("gender", "M");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    }
//    
//    @Test
//    public void getUserDetailswithoutDevice() throws Exception{
//    	String requestDeviceId = "";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "SINGH SAGAR");
//		jsonBody.put("country", "IND");
//		jsonBody.put("documentNumber", "R9079271");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2028-01-23");
//		jsonBody.put("gender", "M");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    	
//    	
//    }
//    
//    @Test
//    public void getUserDetailswithoutUserAgent() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "SINGH SAGAR");
//		jsonBody.put("country", "IND");
//		jsonBody.put("documentNumber", "R9079271");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2028-01-23");
//		jsonBody.put("gender", "M");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    }
//    
//    @Test
//    public void getUserDetailswithoutAuth() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "SINGH SAGAR");
//		jsonBody.put("country", "IND");
//		jsonBody.put("documentNumber", "R9079271");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2028-01-23");
//		jsonBody.put("gender", "M");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    	
//    }
//    
//    @Test
//    public void getUserwithInvalidDevice() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//    	Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "SINGH SAGAR");
//		jsonBody.put("country", "IND");
//		jsonBody.put("documentNumber", "R9079271");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2028-01-23");
//		jsonBody.put("gender", "M");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(422)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Unable to process request as data is invalid.");
//    	
//    }
//    @Test
//	public void getUserDetailswithInvalidGeo() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//    	Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "SINGH SAGAR");
//		jsonBody.put("country", "IND");
//		jsonBody.put("documentNumber", "R9079271");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2028-01-23");
//		jsonBody.put("gender", "M");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(422)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Unable to process request as data is invalid.");
//    	
//    }
//    
//    @Test
//    public void getUserDetailswithInvalidUser() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//    	Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "SINGH SAGAR");
//		jsonBody.put("country", "IND");
//		jsonBody.put("documentNumber", "R9079271");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2028-01-23");
//		jsonBody.put("gender", "M");
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(422)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Unable to process request as data is invalid.");
//    	
//    	
//    }
//    
//    @Test
//    public void getUserwithoutName() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//    	Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "SINGH SAGAR");
//		jsonBody.put("country", "IND");
//		jsonBody.put("documentNumber", "R9079271");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2028-01-23");
//		jsonBody.put("gender", "M");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    	
//    
//    }
//    
//    @Test
//    public void getUserDetailswithoutCountry() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    }
//    
//    @Test
//    public void getUserDetailswithoutDocumentNumber() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    }
//    
//    @Test
//    public void getUserDetailswithoutDocumentType() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    }
//    
//    @Test
//    public void getUserDetailswithoutDateofBirth() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    }
//    
//    @Test
//    public void getUserDetailswithoutDocumentExpiryDate() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    	
//    }
//    
//    @Test
//    public void getUserDetailswithoutGender() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    }
//    
//    @Test
//    public void getUserDetailswithoutSignature() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(400)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_PARAM_MISSING");
//		 assertEquals(description,"Required values missing.");
//    }
//    
//    @Test
//    public void getUserDetailswithInvalidfullname() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(422)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Invalid data.");
//    	
//    }
//    @Test
//    public void getUserDetailswithInvalidCountry() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(422)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Invalid data.");
//    	
//    }
//    
//    @Test
//    public void getUserDetailswithInvalidDocumentNumber() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(422)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Invalid data.");
//    }
//    
//    @Test
//    public void getUserDetailswithInvalidDocumentType() throws Exception{
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(422)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Invalid data.");
//    }
//    @Test
//    public void getUserDetailswithInvalidGender() throws Exception {
//    	String requestDeviceId = "3efe6bbeb55f4411";
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("fullName", "");
//		jsonBody.put("country", "");
//		jsonBody.put("documentNumber", "");
//		jsonBody.put("documentType", "");
//		jsonBody.put("dateOfBirth", "");
//		jsonBody.put("documentExpiryDate", "");
//		jsonBody.put("gender", "");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(422)
//				.log().all()
//				.extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Invalid data.");
//    	
//    }
//     @Test
//     public void getUserDetailswithInvalidDateofBirth() throws Exception {
//    	 String requestDeviceId = "3efe6bbeb55f4411";
//     	ObjectMapper objectMapper = new ObjectMapper();
// 		Map<String, Object> jsonBody = new HashMap<>();
// 		jsonBody.put("fullName", "");
// 		jsonBody.put("country", "");
// 		jsonBody.put("documentNumber", "");
// 		jsonBody.put("documentType", "");
// 		jsonBody.put("dateOfBirth", "");
// 		jsonBody.put("documentExpiryDate", "");
// 		jsonBody.put("gender", "");
//
// 		// Generate signature
// 		String data = objectMapper.writeValueAsString(jsonBody);
// 		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
// 		
// 		jsonBody.put("signature", requestSignature);
// 		
// 		Response response = given()
// 				.header("X-GEO-Location", "12,12")
// 				.header("X-Device-Id", requestDeviceId)
// 				.header("User-Agent", "NepalTravelApp/1.0.0 android")
// 				.contentType("application/json")
// 				.body(jsonBody)
// 				.when()
// 				.post("/profile")
// 				.then()
// 				.statusCode(422)
// 				.log().all()
// 				.extract().response();
// 		
// 		 String code = response.jsonPath().getString("code");
// 	     String description = response.jsonPath().getString("description");
// 	     String signature = response.jsonPath().getString("signature");
// 	     
// 	     assertNotNull(description, "Description is missing from the response");
// 	     assertNotNull(code, "Code is missing");
// 	     assertNotNull(signature,"signature is missing");
// 	     
// 	     assertFalse(description.isEmpty(), "Description is empty");
// 	     assertFalse(code.isEmpty(), "Code is empty");
// 	     assertFalse(signature.isEmpty(),"signature is empty");
// 	     
// 	     assertEquals(code,"GNR_INVALID_DATA");
// 		 assertEquals(description,"Invalid data.");
//     }
//     
//     @Test
//     public void getUserDetailswithInvalidExpiryDate() throws Exception {
//    	 String requestDeviceId = "3efe6bbeb55f4411";
//     	ObjectMapper objectMapper = new ObjectMapper();
// 		Map<String, Object> jsonBody = new HashMap<>();
// 		jsonBody.put("fullName", "");
// 		jsonBody.put("country", "");
// 		jsonBody.put("documentNumber", "");
// 		jsonBody.put("documentType", "");
// 		jsonBody.put("dateOfBirth", "");
// 		jsonBody.put("documentExpiryDate", "");
// 		jsonBody.put("gender", "");
//
// 		// Generate signature
// 		String data = objectMapper.writeValueAsString(jsonBody);
// 		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
// 		
// 		jsonBody.put("signature", requestSignature);
// 		
// 		Response response = given()
// 				.header("X-GEO-Location", "12,12")
// 				.header("X-Device-Id", requestDeviceId)
// 				.header("User-Agent", "NepalTravelApp/1.0.0 android")
// 				.contentType("application/json")
// 				.body(jsonBody)
// 				.when()
// 				.post("/profile")
// 				.then()
// 				.statusCode(422)
// 				.log().all()
// 				.extract().response();
// 		
// 		 String code = response.jsonPath().getString("code");
// 	     String description = response.jsonPath().getString("description");
// 	     String signature = response.jsonPath().getString("signature");
// 	     
// 	     assertNotNull(description, "Description is missing from the response");
// 	     assertNotNull(code, "Code is missing");
// 	     assertNotNull(signature,"signature is missing");
// 	     
// 	     assertFalse(description.isEmpty(), "Description is empty");
// 	     assertFalse(code.isEmpty(), "Code is empty");
// 	     assertFalse(signature.isEmpty(),"signature is empty");
// 	     
// 	     assertEquals(code,"GNR_INVALID_DATA");
// 		 assertEquals(description,"Invalid data.");
//     }
//     
//     @Test
//     public void getUserDetailswithInvalidSignature() throws Exception {
//    	 String requestDeviceId = "3efe6bbeb55f4411";
//     	ObjectMapper objectMapper = new ObjectMapper();
// 		Map<String, Object> jsonBody = new HashMap<>();
// 		jsonBody.put("fullName", "");
// 		jsonBody.put("country", "");
// 		jsonBody.put("documentNumber", "");
// 		jsonBody.put("documentType", "");
// 		jsonBody.put("dateOfBirth", "");
// 		jsonBody.put("documentExpiryDate", "");
// 		jsonBody.put("gender", "");
//
// 		// Generate signature
// 		String data = objectMapper.writeValueAsString(jsonBody);
// 		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
// 		
// 		jsonBody.put("signature", requestSignature);
// 		
// 		Response response = given()
// 				.header("X-GEO-Location", "12,12")
// 				.header("X-Device-Id", requestDeviceId)
// 				.header("User-Agent", "NepalTravelApp/1.0.0 android")
// 				.contentType("application/json")
// 				.body(jsonBody)
// 				.when()
// 				.post("/profile")
// 				.then()
// 				.statusCode(401)
// 				.log().all()
// 				.extract().response();
// 		
// 		 String code = response.jsonPath().getString("code");
// 	     String description = response.jsonPath().getString("description");
// 	     String signature = response.jsonPath().getString("signature");
// 	     
// 	     assertNotNull(description, "Description is missing from the response");
// 	     assertNotNull(code, "Code is missing");
// 	     assertNotNull(signature,"signature is missing");
// 	     
// 	     assertFalse(description.isEmpty(), "Description is empty");
// 	     assertFalse(code.isEmpty(), "Code is empty");
// 	     assertFalse(signature.isEmpty(),"signature is empty");
// 	     
// 	     assertEquals(code,"GNR_AUTHENTICATION_FAIL");
// 		 assertEquals(description,"Authentication Failed.");
//     }
//     
//     @Test
//     public void getUserDetailswhenServerdown() throws Exception {
//    	 String requestDeviceId = "3efe6bbeb55f4411";
//      	ObjectMapper objectMapper = new ObjectMapper();
//  		Map<String, Object> jsonBody = new HashMap<>();
//  		jsonBody.put("fullName", "");
//  		jsonBody.put("country", "");
//  		jsonBody.put("documentNumber", "");
//  		jsonBody.put("documentType", "");
//  		jsonBody.put("dateOfBirth", "");
//  		jsonBody.put("documentExpiryDate", "");
//  		jsonBody.put("gender", "");
//
//  		// Generate signature
//  		String data = objectMapper.writeValueAsString(jsonBody);
//  		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//  		
//  		jsonBody.put("signature", requestSignature);
//  		
//  		Response response = given()
//  				.header("X-GEO-Location", "12,12")
//  				.header("X-Device-Id", requestDeviceId)
//  				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//  				.contentType("application/json")
//  				.body(jsonBody)
//  				.when()
//  				.post("/profile")
//  				.then()
//  				.statusCode(500)
//  				.log().all()
//  				.extract().response();
//  		
//  		 String code = response.jsonPath().getString("code");
//  	     String description = response.jsonPath().getString("description");
//  	     String signature = response.jsonPath().getString("signature");
//  	     
//  	     assertNotNull(description, "Description is missing from the response");
//  	     assertNotNull(code, "Code is missing");
//  	     assertNotNull(signature,"signature is missing");
//  	     
//  	     assertFalse(description.isEmpty(), "Description is empty");
//  	     assertFalse(code.isEmpty(), "Code is empty");
//  	     assertFalse(signature.isEmpty(),"signature is empty");
//  	     
//  	     assertEquals(code,"GNR_ERR");
//  		 assertEquals(description,"Internal Server Error");
//    	 
//     }
//     @Test
//     public void getUserDetailswhenUserisVerified() throws Exception {
//    	 String requestDeviceId = "3efe6bbeb55f4411";
//       	ObjectMapper objectMapper = new ObjectMapper();
//   		Map<String, Object> jsonBody = new HashMap<>();
//   		jsonBody.put("fullName", "");
//   		jsonBody.put("country", "");
//   		jsonBody.put("documentNumber", "");
//   		jsonBody.put("documentType", "");
//   		jsonBody.put("dateOfBirth", "");
//   		jsonBody.put("documentExpiryDate", "");
//   		jsonBody.put("gender", "");
//
//   		// Generate signature
//   		String data = objectMapper.writeValueAsString(jsonBody);
//   		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//   		
//   		jsonBody.put("signature", requestSignature);
//   		
//   		Response response = given()
//   				.header("X-GEO-Location", "12,12")
//   				.header("X-Device-Id", requestDeviceId)
//   				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//   				.contentType("application/json")
//   				.body(jsonBody)
//   				.when()
//   				.post("/profile")
//   				.then()
//   				.statusCode(403)
//   				.log().all()
//   				.extract().response();
//   		
//   		 String code = response.jsonPath().getString("code");
//   	     String description = response.jsonPath().getString("description");
//   	     String signature = response.jsonPath().getString("signature");
//   	     
//   	     assertNotNull(description, "Description is missing from the response");
//   	     assertNotNull(code, "Code is missing");
//   	     assertNotNull(signature,"signature is missing");
//   	     
//   	     assertFalse(description.isEmpty(), "Description is empty");
//   	     assertFalse(code.isEmpty(), "Code is empty");
//   	     assertFalse(signature.isEmpty(),"signature is empty");
//   	     
//   	     assertEquals(code,"GNR_FORBIDDEN");
//   		 assertEquals(description,"Internal Server Error");
//    	 
//     }
//     @Test
//     public void getUserDetailswithoutSelfie() throws Exception {
//    	 String requestDeviceId = "3efe6bbeb55f4411";
//        	ObjectMapper objectMapper = new ObjectMapper();
//    		Map<String, Object> jsonBody = new HashMap<>();
//    		jsonBody.put("fullName", "");
//    		jsonBody.put("country", "");
//    		jsonBody.put("documentNumber", "");
//    		jsonBody.put("documentType", "");
//    		jsonBody.put("dateOfBirth", "");
//    		jsonBody.put("documentExpiryDate", "");
//    		jsonBody.put("gender", "");
//
//    		// Generate signature
//    		String data = objectMapper.writeValueAsString(jsonBody);
//    		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//    		
//    		jsonBody.put("signature", requestSignature);
//    		
//    		Response response = given()
//    				.header("X-GEO-Location", "12,12")
//    				.header("X-Device-Id", requestDeviceId)
//    				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//    				.contentType("application/json")
//    				.body(jsonBody)
//    				.when()
//    				.post("/profile")
//    				.then()
//    				.statusCode(406)
//    				.log().all()
//    				.extract().response();
//    		
//    		 String code = response.jsonPath().getString("code");
//    	     String description = response.jsonPath().getString("description");
//    	     String signature = response.jsonPath().getString("signature");
//    	     
//    	     assertNotNull(description, "Description is missing from the response");
//    	     assertNotNull(code, "Code is missing");
//    	     assertNotNull(signature,"signature is missing");
//    	     
//    	     assertFalse(description.isEmpty(), "Description is empty");
//    	     assertFalse(code.isEmpty(), "Code is empty");
//    	     assertFalse(signature.isEmpty(),"signature is empty");
//    	     
//    	     assertEquals(code,"GNR_NOT_ALLOWED");
//    		 assertEquals(description,"Upload selfie and document first.");
//    	 
//     }
    
}

package Profile;

import org.testng.annotations.*;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class profile {
//	String AuthToken;
	String secretKey;
	String requestDeviceId = "visitor-app-device"; 
	String input_email = "vivek@moco.com.np";
	String input_pin = "123654";
	
	String AuthToken;
//	String requestDeviceId = "sandesh-thapa-app"; 
//	String input_email = "sandeshthapa@moco.com.np";
//	String input_pin = "446646";
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
	     secretKey = signatureCreate.decryptAES(response2.jsonPath().getString("sessionKey"),secretKey1);
	}

//    @Test
//    public void UpdateUserDetails() throws Exception {
//    	RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		
//    	ObjectMapper objectMapper = new ObjectMapper();
//    	Map<String, Object> jsonBody = new LinkedHashMap<>();
//		jsonBody.put("fullName", "SARAH JANE DIAS");
//		jsonBody.put("country", "NPL");
//		jsonBody.put("documentNumber", "P258963");
//		jsonBody.put("documentType", "PASSPORT");
//		jsonBody.put("dateOfBirth", "1998-02-24");
//		jsonBody.put("documentExpiryDate", "2030-01-23");
//		jsonBody.put("gender", "F");
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//		
//		jsonBody.put("signature", requestSignature);
//		System.out.println(jsonBody);
//		//jsonBody.put("signature", requestSignature)
//		
//		Response response = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.header("X-AUTH-TOKEN",AuthToken)
//			    .contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("/profile")
//				.then()
//				.statusCode(200)
//				.log().all()
//				.extract().response();
//		
//		response.prettyPrint();
//		
//		 String signature = response.jsonPath().getString("signature");
//	     
//	     String fullName = response.jsonPath().getString("fullName");
//	     String country = response.jsonPath().getString("country");
//	     String documentNumber = response.jsonPath().getString("documentNumber");
//	     String documentType = response.jsonPath().getString("documentType");
//	     String dateOfBirth = response.jsonPath().getString("dateOfBirth");
//	     String documentExpiryDate = response.jsonPath().getString("documentExpiryDate");
//	     String responseemail = response.jsonPath().getString("email");
//	     String gender = response.jsonPath().getString("gender");
//	     String status = response.jsonPath().getString("status");
//	     String verificationStatus = response.jsonPath().getString("verificationStatus");
//	     String loginAttemptCount = response.jsonPath().getString("loginAttemptCountPin");
//	     String isBiometric = response.jsonPath().getString("loginAttemptCountBiometric");
//		
//	   //check if response includes null value
//	        assertNotNull(signature, "signature is missing");
//	        assertNotNull(fullName, "fullname is missing");
//	        assertNotNull(country, "country is missing");
//	        assertNotNull(documentNumber, "documentNumber is missing");
//	        assertNotNull(documentType, "documentType is missing");
//	        assertNotNull(dateOfBirth, "dateOfBirth is missing");
//	        assertNotNull(documentExpiryDate, "documentExpiryDate is missing");
//	        assertNotNull(responseemail, "responseemail is missing");
//	        assertNotNull(gender, "gender is missing");
//	        assertNotNull(status, "status is missing");
//	        assertNotNull(verificationStatus, "verificationStatus is missing");
//	        assertNotNull(loginAttemptCount, "loginAttemptCount is missing");
//	        assertNotNull(isBiometric, "isBiometric is missing");
//	        
//	        //check is response includes empty values
//	        assertFalse(signature.isEmpty(), "Signature is empty in the response");
//	        assertFalse(fullName.isEmpty(), "fullname is missing");
//	        assertFalse(country.isEmpty(), "country is missing");
//	        assertFalse(documentNumber.isEmpty(), "documentNumber is missing");
//	        assertFalse(documentType.isEmpty(), "documentType is missing");
//	        assertFalse(dateOfBirth.isEmpty(), "dateOfBirth is missing");
//	        assertFalse(documentExpiryDate.isEmpty(), "documentExpiryDate is missing");
//	        assertFalse(responseemail.isEmpty(), "responseemail is missing");
//	        assertFalse(gender.isEmpty(), "gender is missing");
//	        assertFalse(status.isEmpty(), "status is missing");
//	        assertFalse(verificationStatus.isEmpty(), "verificationStatus is missing");
//	        assertFalse(loginAttemptCount.isEmpty(), "loginAttemptCount is missing");
//	        assertFalse(isBiometric.isEmpty(), "isBiometric is missing");
//	        
//	        //matching response signature with calculated hash
//	        
////	        Map<String, Object> profile = new LinkedHashMap<>();
////	        profile.put("fullName",fullName);
////	        profile.put("country",country);
////	        profile.put("documentNumber",documentNumber);
////	        profile.put("documentType",documentType);
////	        profile.put("dateOfBirth",dateOfBirth);
////	        profile.put("documentExpiryDate", documentExpiryDate);
////	        profile.put("email",responseemail);
////	        profile.put("gender",gender);
////	        profile.put("status",status);
////	        profile.put("verificationStatus",verificationStatus);
////	        profile.put("loginAttemptCountPin",loginAttemptCount);
////	        profile.put("loginAttemptCountBiometric",isBiometric);
////
////	        String partialJson = objectMapper.writeValueAsString(profile);
////	        String partialSignature = signatureCreate.generateHMACSHA256(partialJson, secretKey);
////	        assertEquals(signature, partialSignature);
//	        
//	        
//	        String jsonResponse = response.getBody().asString();
//	        // Parse JSON string to JsonObject
//	        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
//	        //remove signature
//	        jsonObject.remove("signature");
//	        //set the jsonObject to string
//	        String dataToSign = jsonObject.toString();
//	        String calculatedSignature = signatureCreate.generateHMACSHA256(dataToSign, secretKey);
//	        
//	        assertEquals(signature, calculatedSignature, "Response signature doesn't match");
//	        
//	           AuthToken = response.getHeader("X-AUTH-TOKEN");
//	            	
//  }
    
    @Test
    public void getUserDetailswithoutGeo() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
		Response response = given()
				.header("X-GEO-Location", "")
				.header("X-Device-Id", requestDeviceId)
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-AUTH-TOKEN",AuthToken)
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("/profile")
				.then()
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
    }
//    
    @Test
    public void getUserDetailswithoutDevice() throws Exception{
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
		Response response = given()
				.header("X-GEO-Location", "12,12")
				.header("X-Device-Id", "")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.header("X-AUTH-TOKEN",AuthToken)
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("/profile")
				.then()
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
    	
    	
    }
//    
    @Test
    public void getUserDetailswithoutUserAgent() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
		Response response = given()
				.header("X-GEO-Location", "12,12")
				.header("X-Device-Id", requestDeviceId)
				.header("User-Agent", "")
				.header("X-AUTH-TOKEN",AuthToken)
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("/profile")
				.then()
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
    }
//    
    @Test
    public void getUserDetailswithoutAuth() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
		Response response = given()
				.header("X-GEO-Location", "12,12")
				.header("X-Device-Id", requestDeviceId)
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.header("X-AUTH-TOKEN","")
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("/profile")
				.then()
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
    	
    }
//    
    @Test
    public void getUserwithInvalidDevice() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
		Response response = given()
				.header("X-GEO-Location", "12,12")
				.header("X-Device-Id", "cc^")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.header("X-AUTH-TOKEN",AuthToken)
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("/profile")
				.then()
				.statusCode(422)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	   //  //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     //
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     //
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid device Id found.");
    	
    }
    @Test
	public void getUserDetailswithInvalidGeo() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
		Response response = given()
				.header("X-GEO-Location", "12@12")
				.header("X-Device-Id", requestDeviceId)
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.header("X-AUTH-TOKEN",AuthToken)
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("/profile")
				.then()
				.statusCode(422)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid Geo location found.");
    	
    }
//    
    @Test
    public void getUserDetailswithInvalidUserAuth() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");
		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
		Response response = given()
				.header("X-GEO-Location", "12,12")
				.header("X-Device-Id", requestDeviceId)
				.header("User-Agent", "NepalTravelA1.0.0 android")
				.header("X-AUTH-TOKEN",AuthToken)
				.contentType("application/json")
				.body(jsonBody)
				.when()
				.post("/profile")
				.then()
				.statusCode(422)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid user agent found.");
    	
    	
    }
//    
    @Test
    public void getUserwithoutName() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Object> jsonBody = new LinkedHashMap<>();
    	jsonBody.put("fullName", "");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		// assertEquals(description,"FullName  cannot be empty.");
    	
    
    }
//    
    @Test
    public void getUserDetailswithoutCountry() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Country cannot be empty.");
    }
//    
    @Test
    public void getUserDetailswithoutDocumentNumber() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Document Number cannot be empty.");
    }
//    
    @Test
    public void getUserDetailswithoutDocumentType() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Document Type cannot be blank.");
    }
//    
    @Test
    public void getUserDetailswithoutDateofBirth() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
    }
//    
    @Test
    public void getUserDetailswithoutDocumentExpiryDate() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
    	
    }
//    
    @Test
    public void getUserDetailswithoutGender() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Gender cannot be empty.");
    }
//    
    @Test
    public void getUserDetailswithoutSignature() throws Exception {
    	
    	//ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		//String data = objectMapper.writeValueAsString(jsonBody);
		//String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", "");
		
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
				.statusCode(400)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Signature cannot be blank.");
    }
//    
//    @Test
//    public void getUserDetailswithInvalidfullname() throws Exception {
//    	
//    	ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> jsonBody = new LinkedHashMap<>();
//		jsonBody.put("fullName", "SIN.()GH SAGAR");
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
//				.header("X-AUTH-TOKEN",AuthToken)
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
//	     //String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     
//	     
//	     assertEquals(code,"GNR_INVALID_DATA");
//		 assertEquals(description,"Invalid data.");
//    	
//    }
    @Test
    public void getUserDetailswithInvalidCountry() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "XYMMMM");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(422)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid Country code.");
    	
    }
    
    @Test
    public void getUserDetailswithInvalidDocumentNumber() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "X@34.()");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(422)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid Document Number.");
    }
    
    @Test
    public void getUserDetailswithInvalidDocumentType() throws Exception{
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "RATION");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");
		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(422)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid Document Type.");
    }
    @Test
    public void getUserDetailswithInvalidGender() throws Exception {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonBody = new LinkedHashMap<>();
		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "P");

		// Generate signature
		String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		
		jsonBody.put("signature", requestSignature);
		
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
				.statusCode(422)
				.log().all()
				.extract().response();
		
		 String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid Gender.");
    	
    }
     @Test
     public void getUserDetailswithInvalidDateofBirth() throws Exception {
    	 
     	ObjectMapper objectMapper = new ObjectMapper();
 		Map<String, Object> jsonBody = new LinkedHashMap<>();
 		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "2026-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

 		// Generate signature
 		String data = objectMapper.writeValueAsString(jsonBody);
 		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
 		
 		jsonBody.put("signature", requestSignature);
 		
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
 				.statusCode(422)
 				.log().all()
 				.extract().response();
 		
 		 String code = response.jsonPath().getString("code");
 	     String description = response.jsonPath().getString("description");
 	     //String signature = response.jsonPath().getString("signature");
 	     
 	     assertNotNull(description, "Description is missing from the response");
 	     assertNotNull(code, "Code is missing");
 	     
 	     
 	     assertFalse(description.isEmpty(), "Description is empty");
 	     assertFalse(code.isEmpty(), "Code is empty");
 	     
 	     
 	     assertEquals(code,"GNR_INVALID_DATA");
 		 assertEquals(description,"Invalid date of birth.");
     }
     
     @Test
     public void getUserDetailswithInvalidExpiryDate() throws Exception {
    	 
     	ObjectMapper objectMapper = new ObjectMapper();
 		Map<String, Object> jsonBody = new LinkedHashMap<>();
 		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "252222-01-23");
		jsonBody.put("gender", "M");

 		// Generate signature
 		String data = objectMapper.writeValueAsString(jsonBody);
 		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
 		
 		jsonBody.put("signature", requestSignature);
 		
 		System.out.println(jsonBody);
 		
// 		Response response = given()
// 				.header("X-GEO-Location", "12,12")
// 				.header("X-Device-Id", requestDeviceId)
// 				.header("User-Agent", "NepalTravelApp/1.0.0 android")
// 				.header("X-AUTH-TOKEN",AuthToken)
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
// 	     //String signature = response.jsonPath().getString("signature");
// 	     
// 	     assertNotNull(description, "Description is missing from the response");
// 	     assertNotNull(code, "Code is missing");
// 	     
// 	     
// 	     assertFalse(description.isEmpty(), "Description is empty");
// 	     assertFalse(code.isEmpty(), "Code is empty");
// 	     
// 	     
// 	     assertEquals(code,"GNR_INVALID_DATA");
// 		 assertEquals(description,"Invalid Data.");
     }
     
     @Test
     public void getUserDetailswithInvalidSignature() throws Exception {
    	 
     	//ObjectMapper objectMapper = new ObjectMapper();
 		Map<String, Object> jsonBody = new LinkedHashMap<>();
 		jsonBody.put("fullName", "SINGH SAGAR");
		jsonBody.put("country", "IND");
		jsonBody.put("documentNumber", "R9079271");
		jsonBody.put("documentType", "PASSPORT");
		jsonBody.put("dateOfBirth", "1998-02-24");
		jsonBody.put("documentExpiryDate", "2028-01-23");
		jsonBody.put("gender", "M");

 		// Generate signature
 		//String data = objectMapper.writeValueAsString(jsonBody);
 		//String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
 		
 		jsonBody.put("signature", "cc");
 		
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
 				.statusCode(401)
 				.log().all()
 				.extract().response();
 		
 		 String code = response.jsonPath().getString("code");
 	     String description = response.jsonPath().getString("description");
 	     //String signature = response.jsonPath().getString("signature");
 	     
 	     assertNotNull(description, "Description is missing from the response");
 	     assertNotNull(code, "Code is missing");
 	     
 	     
 	     assertFalse(description.isEmpty(), "Description is empty");
 	     assertFalse(code.isEmpty(), "Code is empty");
 	     
 	     
 	     assertEquals(code,"GNR_AUTHENTICATION_FAIL");
 		 assertEquals(description,"Authentication failed.");
     }
     
     @Test
     public void getUserDetailswhenServerdown() throws Exception {
    	 
      	ObjectMapper objectMapper = new ObjectMapper();
  		Map<String, Object> jsonBody = new LinkedHashMap<>();
  		jsonBody.put("fullName", "");
  		jsonBody.put("country", "");
  		jsonBody.put("documentNumber", "");
  		jsonBody.put("documentType", "");
  		jsonBody.put("dateOfBirth", "");
  		jsonBody.put("documentExpiryDate", "");
  		jsonBody.put("gender", "");

  		// Generate signature
  		String data = objectMapper.writeValueAsString(jsonBody);
  		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
  		
  		jsonBody.put("signature", requestSignature);
  		
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
  				.statusCode(500)
  				.log().all()
  				.extract().response();
  		
  		 String code = response.jsonPath().getString("code");
  	     String description = response.jsonPath().getString("description");
  	     //String signature = response.jsonPath().getString("signature");
  	     
  	     assertNotNull(description, "Description is missing from the response");
  	     assertNotNull(code, "Code is missing");
  	     
  	     
  	     assertFalse(description.isEmpty(), "Description is empty");
  	     assertFalse(code.isEmpty(), "Code is empty");
  	     
  	     
  	     assertEquals(code,"GNR_ERR");
  		 assertEquals(description,"Internal Server Error");
    	 
     }
////     @Test
////     public void getUserDetailswhenUserisVerified() throws Exception {
////    	 
////       	ObjectMapper objectMapper = new ObjectMapper();
////   		Map<String, Object> jsonBody = new LinkedHashMap<>();
////   		jsonBody.put("fullName", "SINGH SAGAR");
////		jsonBody.put("country", "IND");
////		jsonBody.put("documentNumber", "R9079271");
////		jsonBody.put("documentType", "PASSPORT");
////		jsonBody.put("dateOfBirth", "1998-02-24");
////		jsonBody.put("documentExpiryDate", "2028-01-23");
////		jsonBody.put("gender", "M");
////
////   		// Generate signature
////   		String data = objectMapper.writeValueAsString(jsonBody);
////   		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
////   		
////   		jsonBody.put("signature", requestSignature);
////   		
////   		Response response = given()
////   				.header("X-GEO-Location", "12,12")
////   				.header("X-Device-Id", requestDeviceId)
////   				.header("User-Agent", "NepalTravelApp/1.0.0 android")
////   				.header("X-AUTH-TOKEN",AuthToken)
////   				.contentType("application/json")
////   				.body(jsonBody)
////   				.when()
////   				.post("/profile")
////   				.then()
////   				.statusCode(403)
////   				.log().all()
////   				.extract().response();
////   		
////   		 String code = response.jsonPath().getString("code");
////   	     String description = response.jsonPath().getString("description");
////   	     //String signature = response.jsonPath().getString("signature");
////   	     
////   	     assertNotNull(description, "Description is missing from the response");
////   	     assertNotNull(code, "Code is missing");
////   	     
////   	     
////   	     assertFalse(description.isEmpty(), "Description is empty");
////   	     assertFalse(code.isEmpty(), "Code is empty");
////   	     
////   	     
////   	     assertEquals(code,"GNR_FORBIDDEN");
////   		 assertEquals(description,"Profile already in verified or pending state.");
////    	 
////     }
////     @Test
////     public void getUserDetailswithoutSelfie() throws Exception {
////    	 
////        	ObjectMapper objectMapper = new ObjectMapper();
////    		Map<String, Object> jsonBody = new LinkedHashMap<>();
////    		jsonBody.put("fullName", "SINGH SAGAR");
////    		jsonBody.put("country", "IND");
////    		jsonBody.put("documentNumber", "R9079271");
////    		jsonBody.put("documentType", "PASSPORT");
////    		jsonBody.put("dateOfBirth", "1998-02-24");
////    		jsonBody.put("documentExpiryDate", "2028-01-23");
////    		jsonBody.put("gender", "M");
////
////    		// Generate signature
////    		String data = objectMapper.writeValueAsString(jsonBody);
////    		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
////    		
////    		jsonBody.put("signature", requestSignature);
////    		
////    		Response response = given()
////    				.header("X-GEO-Location", "12,12")
////    				.header("X-Device-Id", requestDeviceId)
////    				.header("User-Agent", "NepalTravelApp/1.0.0 android")
////    				.header("X-AUTH-TOKEN",AuthToken)
////    				.contentType("application/json")
////    				.body(jsonBody)
////    				.when()
////    				.post("/profile")
////    				.then()
////    				.statusCode(406)
////    				.log().all()
////    				.extract().response();
////    		
////    		 String code = response.jsonPath().getString("code");
////    	     String description = response.jsonPath().getString("description");
////    	     //String signature = response.jsonPath().getString("signature");
////    	     
////    	     assertNotNull(description, "Description is missing from the response");
////    	     assertNotNull(code, "Code is missing");
////    	     
////    	     
////    	     assertFalse(description.isEmpty(), "Description is empty");
////    	     assertFalse(code.isEmpty(), "Code is empty");
////    	     
////    	     
////    	     assertEquals(code,"GNR_NOT_ALLOWED");
////    		 assertEquals(description,"Upload selfie and document first.");
////    	 
////  }
//     @AfterClass
// 	public void logout() {
// 		baseURI = "https://visitor0.moco.com.np/visitor";
// 	      Response response = given()
// 	          .header("X-GEO-Location", "12,12")
// 	          .header("X-AUTH-TOKEN",AuthToken)
// 	          .header("X-Device-Id", requestDeviceId)
// 	          .header("User-Agent", "NepalTravelApp/1.0.0 android")
// 	      .when()
// 	          .delete("/authenticate");
// 	      response.then().statusCode(200);
// 		
// 	}
    
}

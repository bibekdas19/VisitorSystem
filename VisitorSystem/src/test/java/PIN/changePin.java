package PIN;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

public class changePin {
	String plain_old_pin = "443062";
    String plain_new_pin = "152369";
    String secretKey = "qP9rjBMsyjY1QuIkLf0NuWkxtVqlp+belhQpU7GMyro=";
    String AuthToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ2aXZla0Btb2NvLmNvbS5ucCIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tdHJhdmVsLWFwcCIsImlhdCI6MTc0Njc3OTcyOSwiZXhwIjoxNzQ2ODA5NzI5fQ.2uU_D-VcyiXuQMSRja8cLwK4K-KilgsZWdLi9asSuaYAz4nGXu6WLnT89kobkocN";
	
    @Test
	public void changePinwithValidCredentials() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
    	ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        System.out.println(jsonBody);
        
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
        
	}
	
	@Test
	public void changePinwithoutDevice() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

       // assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
	}
	@Test
	public void changePinwithoutAuthToken() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN","")
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
	}
	@Test
	public void changePinwithoutLocation() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
	}
	@Test
	public void changePinwithoutUserAgent() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
	}
	@Test
	public void changePinwithInvalidDevice() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app@")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid device Id found.");
	}
	
	@Test
	public void changePinwithInvalidAuth() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN","a(8")
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(401)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_AUTHENTICATION_FAIL");
		 assertEquals(description,"Authentication Failed.");
	}
	
	@Test
	public void changePinwithInvalidUserAgent() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravp/1.0. android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid user agent found.");
	}
	
	@Test
	public void changePinwhenNewPinEmpty() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
      //  String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin","");
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}
	
	@Test
	public void changePinwhenNewPinPlain() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        //String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",plain_new_pin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}
	@Test
	public void changePinwhenOldPinEmpty() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
     //   String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin","");
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}
	
	@Test
	public void changePinwhenOldPinPlain() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        //String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",plain_old_pin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
       // String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}
	
	@Test
	public void changePinwhenSignatureEmpty() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        //String data = objectMapper.writeValueAsString(jsonBody);
        //String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", "");
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
       // String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}
	
	@Test
	public void changePinwhenSignatureInvalid() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.encryptAES256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(401)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}
	
	@Test
	public void changePinwhenOldpinSameNewPin() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(409)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
		
	}
	
	@Test
	public void changePinwhenNewPinCriteria() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256("111111", secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}
	@Test
	public void changePinwhenOldPinIncorrect() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.encryptAES256("582688", secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}
	@Test
	public void changePinwhenDifferentkeysEncryption() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
        String oldPin = signatureCreate.generateHMACSHA256(plain_old_pin, secretKey);
        String newPin = signatureCreate.encryptAES256(plain_new_pin, secretKey);
        //construct json
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        //construct signature
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
		 assertEquals(description,"PIN changed successfully. Invalidate JWT. Redirect visitor to login.");
	}

}

package PIN;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

public class changePin {
	String plain_old_pin = "152986";
    String plain_new_pin = "443062";
    String secretKey = "raZpD/w+h8CmFyfBQ1N+zBD4oLFuNqESOC6Ui3l3OMA=";
    String AuthToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ2aXZla0Btb2NvLmNvbS5ucCIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tdHJhdmVsLWFwcCIsImlhdCI6MTc0NjUyMjU0NCwiZXhwIjoxNzQ2NTUyNTQ0fQ.c1CYwaduAWc9ZW83w76LNMcadWW9WKStr6hqT0ItxQPMni9vjl21QxcaR4GkomyV";
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
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travel-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
	public void changePinwhenNewPinEmpty() throws Exception {
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
	public void changePinwhenNewPinPlain() throws Exception {
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
	public void changePinwhenOldPinPlain() throws Exception {
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
	public void changePinwhenSignatureEmpty() throws Exception {
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .post("/pin")
            .then()
                .statusCode(200)
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
	public void changePinwhenOldpinSameNewPin() throws Exception {
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/pin")
            .then()
                .statusCode(200)
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

package PIN;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

public class changePin {
	String plain_old_pin = "123654";
    String plain_new_pin = "123653";
    String AuthToken;
	String requestDeviceId = "visitor-app-device"; 
	String secretKey;
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
	     secretKey = signatureCreate.decryptAES(response2.jsonPath().getString("sessionKey"),secretKey1);
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
                .header("X-Device-Id", requestDeviceId)
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
                .header("X-Device-Id", requestDeviceId)
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
                .header("X-Device-Id", requestDeviceId)
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
                .header("X-Device-Id", requestDeviceId)
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
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", "moco-travels-app")
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
        System.out.println(jsonBody);
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", requestDeviceId)
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
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(500)
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
        
         assertEquals(code,"GNR_ERR");
		 assertEquals(description,"Decryption failed.");
	}
//	
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
                .header("X-Device-Id", requestDeviceId)
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
        
         assertEquals(code,"GNR_PARAM_MISSING");
		 assertEquals(description,"Bad Request.");
	}
//	
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
                .header("X-Device-Id", requestDeviceId)
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
		 assertEquals(description,"Authentication failed.");
	}
//	
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
                .header("X-Device-Id", requestDeviceId)
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
        //String signature = response.jsonPath().getString("signature");

        //assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

       // assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_CONFLICT");
		 assertEquals(description,"Old Pin and new PIN cannot be the same.");
		
	}
//	
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
                .header("X-Device-Id", requestDeviceId)
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
        
         assertEquals(code,"VST_PIN_CRITERIA_FAIL");
		 assertEquals(description,"Change PIN failed as PIN criteria not met.");
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
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(500)
                .log().all()
                .extract().response();
        
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
       // String signature = response.jsonPath().getString("signature");

       // assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

       // assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_ERR");
		 assertEquals(description,"Error while authorizing visitor.");
	}
	
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
//     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-AUTH-TOKEN",AuthToken)
                .header("X-Device-Id", requestDeviceId)
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
		 assertEquals(description,"PIN changed successfully.");
		 
		 //verify signature
	     //matching response signature with calculated hash
	     Map<String, Object> fields = new LinkedHashMap<>();
	     fields.put("code", code);
	     fields.put("description", description);
	     

	     String partialJson = objectMapper.writeValueAsString(fields);
	     String partialSignature = signatureCreate.generateHMACSHA256(partialJson, secretKey);
	     assertEquals(signature, partialSignature);
		 
		 
        
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
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .put("/pin")
            .then()
                .statusCode(500)
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
        
         assertEquals(code,"GNR_ERR");
		 assertEquals(description,"Error while authorizing visitor.");
	}
	
//	@AfterClass
//	public void logout() throws Exception {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//      Response response = given()
//          .header("X-GEO-Location", "12,12")
//          .header("X-AUTH-TOKEN",AuthToken)
//          .header("X-Device-Id", requestDeviceId)
//          .header("User-Agent", "NepalTravelApp/1.0.0 android")
//      .when()
//          .delete("/authenticate");
//      response.then().statusCode(200);
//	}
////         

}

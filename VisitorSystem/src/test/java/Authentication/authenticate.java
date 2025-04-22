package Authentication;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;


public class authenticate {
	 String secretKey;

	    @BeforeClass
	    public void setup() {
	        RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-Device-Id", "3efe6bbeb55f4411")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .when()
	                .get("/key")
	            .then()
	                .statusCode(200)
	                .extract().response();

	        secretKey = response.jsonPath().getString("signOnKey");
	        assertNotNull(secretKey, "Secret key is null!");
	    }


	@Test
	public void GetToken() throws Exception {
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        
        String signature = response.jsonPath().getString("signature");
        String sessionKey = response.jsonPath().getString("sessionKey");
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
        assertNotNull(sessionKey, "sessionKey is missing");
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
        assertFalse(sessionKey.isEmpty(), "sessionKey is missing");
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
        
        assertEquals(requestDeviceId,deviceId);
        assertEquals("John Smith",fullName);
        assertEquals("India",country);
        
        //check if the token is in the present in the response
        assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
        System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));

	}
	
	@Test
	public void AuthenticateWithNoLocation() throws Exception {
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithNoDevice() throws Exception {
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithoutUserAgent() throws Exception {
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithInvalidLocation() throws Exception {
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithInvalidDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "pp")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .body(jsonBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	@Test
	public void AuthenticateWithInvalidUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(jsonBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	@Test
	public void AuthenticateWithMissingEmail() throws Exception {
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithMissingPin() throws Exception {
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithEmptyEmail() throws Exception {
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithInvalidEmail() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.p.com");
        //credentials.put("pin", "1234");

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(jsonBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithEmptyPin() throws Exception{
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithInvalidPin() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234AAA");

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(jsonBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithMissingSignature() throws Exception{
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithEmptySignature() throws Exception{
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
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
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
	public void AuthenticateWithNullValue() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "NULL");
        credentials.put("pin", "1234");

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(jsonBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(401);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithExceedValues() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234566666677");

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(jsonBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(200);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
}

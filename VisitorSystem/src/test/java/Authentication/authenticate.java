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
	                .header("X-Device-Id", "moco-travel-app")
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
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
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
        String loginAttemptCountPin = response.jsonPath().getString("profile.loginAttemptCountPin");
        String loginAttemptCountBiometric = response.jsonPath().getString("profile.loginAttemptCountBiometric");
        String registrationDate = response.jsonPath().getString("profile.registrationDate");
        
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
        assertNotNull(loginAttemptCountPin, "loginAttemptCount is missing");
        assertNotNull(loginAttemptCountBiometric, "isBiometric is missing");
        assertNotNull(registrationDate,"registration date is missing");
        
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
        assertFalse(loginAttemptCountPin.isEmpty(), "loginAttemptCount is missing");
        assertFalse(loginAttemptCountBiometric.isEmpty(), "isBiometric is missing");
        assertFalse(registrationDate.isEmpty(),"registration date is missing");
        
        //check if the device id is same in request and response
        
        assertEquals(requestDeviceId,deviceId);
//        assertEquals("John Smith",fullName);
//        assertEquals("India",country);
//        
        //check if the token is in the present in the response
        assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
        System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));

	}
	@Test
	public void AuthenticateWithNewDevice() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestDeviceId = "moco-travels-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(401)
                .log().all()
                .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"VST_LOGIN_DEVICE_UNRECOGNIZED");
        assertEquals(description,"Bad Request.");
	}
	@Test
	public void AuthenticateWithNoLocation() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
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
        String requestDeviceId = "";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
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
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
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
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12@12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
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
	public void AuthenticateWithInvalidDevice() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestDeviceId = "moco#travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
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
	public void AuthenticateWithInvalidUserAgent() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelA..S1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid user agent found.");

	}
	@Test
	public void AuthenticateWithMissingEmail() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "";
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
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
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        //String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        //credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
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
        String email = "";
        String requestDeviceId = "moco-travel-app";
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
                .post("/authenticate")
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
	public void AuthenticateWithInvalidEmail() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco..com.np";
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Bad Request.");

	}
	
	@Test
	public void AuthenticateWithEmptyPin() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestDeviceId = "moco-travel-app";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);
        //credentials.put("pin", "123426");

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
                .post("/authenticate")
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
	public void AuthenticateWithInvalidPin() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "1AA986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        //String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", "llllas");

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(401)
                .log().all()
                .extract().response();
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
	public void AuthenticateWithMissingSignature() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestDeviceId = "moco-travel-app";
        String plain_pin = "152986";
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//        
//        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
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
        String requestDeviceId = "moco-travel-app";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("pin", "123426");

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
       // String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        
        jsonBody.put("signature", "");
        
     // Send request
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
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
	
//	@Test
//	public void AuthenticateWithNullValue() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        String requestDeviceId = "moco-travel-app";
//        String plain_pin = NULL;
//        Map<String, Object> credentials = new LinkedHashMap<>();
//        credentials.put("email", email);
//        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
//        credentials.put("pin", Pin);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        
//     // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//        
//        jsonBody.put("signature", requestSignature);
//        
//        System.out.println(jsonBody);
//     // Send request
//        Response response = given()
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id", requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/authenticate")
//            .then()
//                .statusCode(401)
//                .log().all()
//                .extract().response();
//       
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//
//        assertNotNull(description, "Description is missing from the response");
//        assertNotNull(code, "Code is missing");
//        assertFalse(description.isEmpty(), "Description is empty");
//        assertFalse(code.isEmpty(), "Code is empty");
//        assertEquals(code,"VST_LOGIN_REMAINING_ATTEMPT_2");
//        assertEquals(description,"Authentication failed.");
//
//	}
	
//	@Test
//	public void AuthenticateWithExceedValues() throws Exception{
//		ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        String requestDeviceId = "moco-travel-app";
//        String plain_pin = "1590909090909092986";
//        Map<String, Object> credentials = new LinkedHashMap<>();
//        credentials.put("email", email);
//        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey);
//        credentials.put("pin", Pin);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        
//     // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//        
//        jsonBody.put("signature", requestSignature);
//        
//        System.out.println(jsonBody);
//     // Send request
//        Response response = given()
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id", requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/authenticate")
//            .then()
//                .statusCode(422)
//                .log().all()
//                .extract().response();
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//
//        assertNotNull(description, "Description is missing from the response");
//        assertNotNull(code, "Code is missing");
//        assertFalse(description.isEmpty(), "Description is empty");
//        assertFalse(code.isEmpty(), "Code is empty");
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//
//	}
	
}

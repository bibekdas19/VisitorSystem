package Device;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;

import java.util.*;

public class deviceRegister {
	String baseURI = "https://visitor0.moco.com.np/visitor";
	String secretKey;
	@BeforeClass
	public void loginUser() throws Exception {
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
                .post("authenticate")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
	}
	
	@Test
	public void registerDevicewithValidParameters() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        

        // Assertions
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String token = response.jsonPath().getString("token");
        String responseSignature = response.jsonPath().getString("signature");

        assertNotNull(responseSignature, "Signature is missing");
        assertNotNull(token, "token is missing from the response");
        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");

        assertFalse(responseSignature.isEmpty(), "Signature is empty");
        assertFalse(token.isEmpty(), "token is empty");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        
        assertEquals(code,"GNR_OK");
        assertEquals(description,"OTP Verified and device registered successfully.");     
	}
	
	@Test
	public void registerDevicewithoutLocation() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
	}
	@Test
	public void registerDevicewithoutDevice() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
	}
    @Test
    public void registerDevicewithoutUserAgent() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    
    @Test
    public void registerDevicewithInvalidLocation() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithInvalidDevice() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithInvalidUserAgent() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithInvalidEmailFormat() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    
    @Test
    public void registerDevicewithoutEmail() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithnewEmail() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithInvalidRequestTimestamp() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithoutRequestTimestamp() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithoutOtp() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithexpiredOtp() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithincorrectOtp() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithInvalidOtp() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithIncorrectToken() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithoutToken() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithoutSignature() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithInvalidSignature() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    @Test
    public void registerDevicewithExistingDevice() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp","");
        jsonBody.put("otp", "");
        jsonBody.put("token", "");
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);
        
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id","sandesh-thapa-app")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/device/otp/verify")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }
    
    
}

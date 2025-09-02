package Device;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;

import java.util.*;

public class deviceRegister {
	String baseURI = "https://visitor0.moco.com.np/visitor";
	String secretKey = "c3fccc2fbae9252d377335148d3ee92bcf185f4b505b5527a8a794e312a1fdb3";
	String Token = "tfH165amRNA1V9lb0ASX--77kFN-p0QwSB4NVB20Lgo";
	String Otp = "341457";
	String pushToken = "12TrYu";
	String requestDeviceId = "samwek-device";
	
	
	@Test
	public void registerDevicewithValidParameters() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        String email = "samwekshakya65@gmail.com";
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
        jsonBody.put("otp", Otp);
        jsonBody.put("token", Token);
        jsonBody.put("pushToken",pushToken);
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
        
     // Add signature
        jsonBody.put("signature", requestSignature);

        System.out.println(jsonBody);
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id",requestDeviceId)
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
        String responseSignature = response.jsonPath().getString("signature");

        assertNotNull(responseSignature, "Signature is missing");
        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");

        assertFalse(responseSignature.isEmpty(), "Signature is empty");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        
        assertEquals(code,"GNR_OK");
        assertEquals(description,"OTP Verified and device registered successfully");     
	}
//	
//	@Test
//	public void registerDevicewithoutLocation() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//	}
//	
//	@Test
//	public void registerDevicewithoutDevice() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id","")
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//	}
//    @Test
//    public void registerDevicewithoutUserAgent() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//    }
//    
//    @Test
//    public void registerDevicewithInvalidLocation() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12@12")
//                .header("X-Device-Id","moco-travel-app")
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(422)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_INVALID_DATA");
//        assertEquals(description,"Invalid Geo location found.");
//    }
//    @Test
//    public void registerDevicewithInvalidDevice() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id","$$")
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(422)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_INVALID_DATA");
//        assertEquals(description,"Invalid device Id found.");
//    }
//    
//    @Test
//    public void registerDevicewithInvalidUserAgent() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelA.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(422)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_INVALID_DATA");
//        assertEquals(description,"Invalid user agent found.");
//    }
//    
//    @Test
//    public void registerDevicewithInvalidEmailFormat() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivekmoco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id","moco-travel-app")
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(422)
//                .log().all()
//                .extract().response();
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_INVALID_DATA");
//        assertEquals(description,"Invalid Data.");
//    }
//    
//    @Test
//    public void registerDevicewithoutEmail() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", "");
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//    }
//    @Test
//    public void registerDevicewithnewEmail() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(200)
//                .log().all()
//                .extract().response();
//    }
//    @Test
//    public void registerDevicewithInvalidRequestTimestamp() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(409)
//                .log().all()
//                .extract().response();
//    }
//    @Test
//    public void registerDevicewithoutRequestTimestamp() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp","");
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//    }
//    @Test
//    public void registerDevicewithoutOtp() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp","");
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//    }
//    @Test
//    public void registerDevicewithexpiredOtp() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(200)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        String status = response.jsonPath().getString("status");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"VST_DEVICE_OTP_FAILED");
//        assertEquals(description,"New device register failed due to invalid OTP.");
//        assertEquals(status,"EXPIRED");
//    }
//    @Test
//    public void registerDevicewithincorrectOtp() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp","1ll");
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(409)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//      String description = response.jsonPath().getString("description");
//    String status = response.jsonPath().getString("status");
//      
//      assertNotNull(code, "code is missing from the response");
//      assertFalse(code.isEmpty(), "code is empty in the response");
//      assertNotNull(description, "description is missing from the response");
//      assertFalse(description.isEmpty(), "description is empty in the response");
//     
//      
//      assertEquals(code,"VST_DEVICE_OTP_FAILED");
//      assertEquals(description,"New device register failed due to invalid OTP.");
//      assertEquals(status,"FAIL");
//    }
//    @Test
//    public void registerDevicewithInvalidOtp() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp","112525");
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(409)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"VST_DEVICE_OTP_FAILED");
//        assertEquals(description,"New device register failed due to invalid OTP.");
//    }
//    @Test
//    public void registerDevicewithIncorrectToken() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp","398837");
//        jsonBody.put("token", "aa");
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(409)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"VST_DEVICE_VERIFICATION_DUPLICATE");
//        assertEquals(description,"New device register failed due to invalid OTP.");
//    }
//    
//    @Test
//    public void registerDevicewithoutToken() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", "");
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//    }
//    @Test
//    public void registerDevicewithoutSignature() throws Exception {
//    	//ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//       // String data = objectMapper.writeValueAsString(jsonBody);
//       // String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", "");
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//    }
//    @Test
//    public void registerDevicewithInvalidSignature() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,"kk");
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id",requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(401)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
//        assertEquals(description,"Authentication failed.");
//    }
//    @Test
//    public void registerDevicewithExistingDevice() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id","sandesh-thapa-app")
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(409)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"VST_DEVICE_VERIFICATION_DUPLICATE");
//        assertEquals(description,"Device already exists.");
//    }
//    @Test
//    public void registerDevicewithInactiveDevice() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken",pushToken);
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id","visitor-app-device")
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(409)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"VST_DEVICE_VERIFICATION_DUPLICATE");
//        assertEquals(description,"Device already exists.");
//    }
//    @Test
//    public void registerDevicewithoutPushToken() throws Exception {
//    	ObjectMapper objectMapper = new ObjectMapper();
//        String email = "vivek@moco.com.np";
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("email", email);
//
//        Map<String, Object> jsonBody = new LinkedHashMap<>();
//        jsonBody.put("credentials", credentials);
//        jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//        jsonBody.put("otp",Otp);
//        jsonBody.put("token", Token);jsonBody.put("pushToken","");
//        
//        // Generate signature
//        String data = objectMapper.writeValueAsString(jsonBody);
//        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//        
//     // Add signature
//        jsonBody.put("signature", requestSignature);
//        
//        Response response = given()
//                .baseUri(baseURI)
//                .header("X-GEO-Location", "12,12")
//                .header("X-Device-Id","visitor-app-device")
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .contentType("application/json")
//                .body(jsonBody)
//            .when()
//                .post("/device/otp/verify")
//            .then()
//                .statusCode(400)
//                .log().all()
//                .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//    }
    
	}


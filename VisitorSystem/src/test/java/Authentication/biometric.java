package Authentication;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;
public class biometric {
	String AuthToken;
	String secretKey;
	String requestDeviceId = "visitor-app-device"; 
	String input_email = "vivek@moco.com.np";
	String input_pin = "147369";
	@BeforeClass
	public void getToken() throws Exception{
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response1 = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 ios")
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
                .header("User-Agent", "NepalTravelApp/1.0.0 ios")
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
	public void setBiometericwithoutDevice() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = new LinkedHashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		String data = objectMapper.writeValueAsString(jsonMap);
        
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		jsonMap.put("signature", requestSignature);
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	        
		response.then().statusCode(400);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
		
	}
	@Test
	public void setBiometricwithoutLocation() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = new LinkedHashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		String data = objectMapper.writeValueAsString(jsonMap);
        
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		jsonMap.put("signature", requestSignature);
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	            response.then().statusCode(400);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
        
	}
    @Test
    public void setBiometricwithoutUserAgent() throws Exception {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = new LinkedHashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		String data = objectMapper.writeValueAsString(jsonMap);
        
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		jsonMap.put("signature", requestSignature);
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	        response.then().statusCode(400);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    @Test
    public void setBiometricwithoutAuth() throws Exception{
    	baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = new LinkedHashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		String data = objectMapper.writeValueAsString(jsonMap);
        
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		jsonMap.put("signature", requestSignature);
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	        response.then().statusCode(400);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    @Test
    public void setBiometricwithInvalidDevice() throws Exception {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = new LinkedHashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		String data = objectMapper.writeValueAsString(jsonMap);
        
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		jsonMap.put("signature", requestSignature);
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "mo@@co-travel-app")
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	            response.then().statusCode(422);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid device Id found.");
    }
    @Test
    public void setBiometricwithInvalidUserAgent() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelAp.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
		response.then().statusCode(422);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid user agent found.");
    }
    @Test
    public void setBiometricwithInvalidLocation() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "1#2")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	        .post("/biometric");
	          response.then().statusCode(422);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid Geo location found.");
    }
    @Test
    public void setBiometricwithInvalidAuth() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN","qq")
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
               response.then().statusCode(401);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
        assertEquals(description,"Authentication Failed.");
    }
    @Test
    public void setBiometricwithoutrequest() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		//jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		//jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	        response.then().statusCode(400);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    @Test
    public void setBiometricwithoutHash() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		//jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	        response.then().statusCode(400);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    @Test
    public void setBiometricwithoutSignature() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		//jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	        response.then().statusCode(400);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
//    @Test
//    public void setBiometricwithInvalidHash() {
//    	baseURI = "https://visitor0.moco.com.np/visitor";
//		Map<String, String> jsonMap = new HashMap<>();
//		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
//		jsonMap.put("signature", "string");
//		
//		Response response = (Response) given()
//	            .header("X-GEO-Location", "12,12")
//	            .header("X-Device-Id", requestDeviceId)
//	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
//	            .header("X-AUTH-TOKEN",AuthToken)
//	            .body(jsonMap)
//	        .when()
//	            .post("/biometric")
//	        .then()
//	            .statusCode(200);
//		
//		String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        //String signature = response.jsonPath().getString("signature");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//       
//        
//        assertEquals(code,"GNR_AUTHENTICATION");
//        assertEquals(description,"Authentication Failed.");
//    }
    @Test
    public void setBiometricwithInvalidSignature() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	       response.then().statusCode(401);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        //String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
       
        
        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
        assertEquals(description,"Authentication failed.");
    }
    @Test
	public void biometricWithValid() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = new LinkedHashMap<>();
		jsonMap.put("hash","tzLhMbbV+G/oQrgXvBg34su8wk1rztNBiR3");
		String data = objectMapper.writeValueAsString(jsonMap);
        
		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
		jsonMap.put("signature", requestSignature);
		
		System.out.println(jsonMap);
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .body(jsonMap)
	        .when()
	            .post("/biometric");
	        response.then().statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
        
        assertEquals(code,"GNR_OK");
        assertEquals(description,"Biometric set up successfully.");
	}
//    @AfterClass
//    public void deleteBiometricLogout() {
//    	baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", requestDeviceId)
//            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
//        .when()
//            .delete("/biometric");
//            response.then().statusCode(200);
//        
//        Response response1 = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", requestDeviceId)
//            .header("User-Agent", "NepalTravelApp/1.0.0 ios")
//        .when()
//            .delete("/authenticate");
//        response1.then().statusCode(200);
//   
//    }
    
}

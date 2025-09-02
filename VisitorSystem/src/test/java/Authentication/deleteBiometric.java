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

import java.util.LinkedHashMap;
import java.util.Map;

public class deleteBiometric {
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
	     secretKey = response2.jsonPath().getString("sessionKey");
	}
	
	
	
	@Test
	public void deleteBiometricwithoutDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", "")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(400)
            .extract().response();
        
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
	public void deleteBiometricwithoutAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(400)
            .extract().response();
        
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
	public void deleteBiometricwithoutLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(400)
            .extract().response();
        
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
	public void deleteBiometricwithoutUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(400)
            .extract().response();
        
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
	public void deleteBiometricwithInvalidDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", "moco-trav@#el-app")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(422)
            .extract().response();
        
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
	public void deleteBiometricwithInvalidLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12##")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(422)
            .extract().response();
        
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
	public void deleteBiometricwithInvalidUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(422)
            .extract().response();
        
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
	public void deleteBiometricwithInvalidAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","22")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(401)
            .extract().response();
        
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
	
//	@Test
//	public void deleteBiometricwithoutsetting() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", requestDeviceId)
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/biometric")
//        .then()
//            .statusCode(404)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        String signature = response.jsonPath().getString("signature");
//	}
	
	@Test
	public void deleteBiometricWithValidCredentials() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(200)
            .extract().response();
        
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
        assertEquals(description,"Biometric deleted successfully.");
	}
	
	@AfterClass
	public void Logout() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate");
        response.then().statusCode(200);
	}
	


}

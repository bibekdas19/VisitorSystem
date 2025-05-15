package Authentication;

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

public class logOut {
	String secretKey;
	String AuthToken;
	String requestDeviceId = "moco-travels-app";
	@Test
	public void setup() throws Exception {
        RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .when()
                .get("/key")
            .then()
                .statusCode(200)
                .extract().response();

        secretKey = response.jsonPath().getString("signOnKey");
        assertNotNull(secretKey, "Secret key is null!");
        
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
        Response response1 = given()
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
        assertEquals(response1.jsonPath().getString("code"),"VST_LOGIN_DISABLED");
       // AuthToken = response1.getHeader("X-AUTH-TOKEN");
	}
	
	
	
//	@Test
//	public void logoutwithoutDevice() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", "")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(400)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//	}
//	@Test
//	public void logoutwithoutAuth() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN","")
//            .header("X-Device-Id", "moco-travels-app")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(400)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//	}
//	@Test
//	public void logoutwithoutLocation() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", "moco-travels-app")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(400)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//	}
//	@Test
//	public void logoutwithoutUserAgent() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", "moco-travels-app")
//            .header("User-Agent", "")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(400)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//	}
//	@Test
//	public void logoutwithInvalidDevice() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", "@##")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(422)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_INVALID_DATA");
//        assertEquals(description,"Invalid device Id found.");
//	}
//	
//	@Test
//	public void logoutwithInvalidLocation() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12AA12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", "moco-travels-app")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(422)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_INVALID_DATA");
//        assertEquals(description,"Invalid Geo location found.");
//	}
//	@Test
//	public void logoutwithInvalidUserAgent() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", "moco-travels-app")
//            .header("User-Agent", "NepalTrave1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(422)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_INVALID_DATA");
//        assertEquals(description,"Invalid user agent found.");
//	}
//	@Test
//	public void logoutwithInvalidAuth() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN","xx")
//            .header("X-Device-Id", "moco-travels-app")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(401)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
//        assertEquals(description,"Authentication Failed.");
//	}
//	@Test
//	public void logoutwithoutLogin() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", "moco-travels-app")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(200)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_PARAM_MISSING");
//        assertEquals(description,"Bad Request.");
//	}
//	@Test
//	public void LogoutwithValidCredentials() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//        Response response = given()
//            .header("X-GEO-Location", "12,12")
//            .header("X-AUTH-TOKEN",AuthToken)
//            .header("X-Device-Id", "moco-travels-app")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//        .when()
//            .delete("/authenticate")
//        .then()
//            .statusCode(200)
//            .extract().response();
//        
//        String code = response.jsonPath().getString("code");
//        String description = response.jsonPath().getString("description");
//        
//        assertNotNull(code, "code is missing from the response");
//        assertFalse(code.isEmpty(), "code is empty in the response");
//        assertNotNull(description, "description is missing from the response");
//        assertFalse(description.isEmpty(), "description is empty in the response");
//        //check if the code value is as per the decided
//        assertEquals(code,"GNR_OK");
//        assertEquals(description," Logged out successfully.");
//	}

}

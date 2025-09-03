package Device;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
public class LoginDevice {
	String AuthToken;
	String requestDeviceId = "vivek-device"; 
	@Test
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
	        String email = "vivek@moco.com.np";
	        String plain_pin = "123654";
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
	              .statusCode(401)
	               // .statusCode(200)
	                 .log().all()
	                .extract().response();
	       
	      System.out.println(secretKey1);
	    
	      //  assertEquals(response2.jsonPath().getString("code"),"VST_LOGIN_DEVICE_UNRECOGNIZED");
	       //assertEquals(response2.jsonPath().getString("description"),"Login from unverified device. OTP Requested Successfully for device registration.");
	     
	}
}

package Device;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.response.Response;

public class validcase {
	String baseURI = "https://visitor0.moco.com.np/visitor";
    String secretKey = "f0572f049cfcc813dd73700cd6e750efa9d10c0360c6655492141da48d6e7a8f";
    String token = "DmvN1R1LN52oDdz9gZrWWR8snU9Vl-yMpglAaqEoGHo";
    String email = "vivek@moco.com.np";
    String requestTimestamp = signatureCreate.generateTimestamp();
   // String plain_pin = "123654";
    String plain_otp = "394045";
    String requestdevice = "vivek-device";
    String pushToken = "abcdefg:7^";
    int deviceScreenWidth = 325;
    int deviceScreenHeight = 485;
    int deviceColorDepth = 6;
    String deviceModel = "CPH256";
	
	@Test
	 public void VerifyOtpwithValidCredentials() throws Exception {
		 ObjectMapper objectMapper = new ObjectMapper();
		 Map<String,Object> credentials = new LinkedHashMap<>();
		 credentials.put("email", email);
	     Map<String, Object> jsonBody = new LinkedHashMap<>();
	     jsonBody.put("credentials",credentials);
	     jsonBody.put("requestTimestamp", requestTimestamp);
	     jsonBody.put("otp", plain_otp);
	     jsonBody.put("token", token);
	     jsonBody.put("pushToken",pushToken);
	     jsonBody.put("deviceModel", deviceModel);
	     jsonBody.put("deviceColorDepth", deviceColorDepth);
	     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
	     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
	     //placing token from device API
	     
	  

	  // Generate signature
	     String data = objectMapper.writeValueAsString(jsonBody);
	     String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
	     
	  // Add signature
	     jsonBody.put("signature", requestSignature);
	  
	     Response response = given()
	             .baseUri(baseURI)
	             .header("X-GEO-Location", "12,12")
	             .header("X-Device-Id",requestdevice)
	             .header("User-Agent", "NepalTravelApp/1.0.0 android")
	             .contentType("application/json")
	             .body(jsonBody)
	         .when()
	             .post("/device/otp/verify")
	         .then()
	             .statusCode(200)
	             .log().all()
	             .extract().response();
	     response.prettyPrint();
	     

	     // Extracting and asserting response values
	     String responseEmail = response.jsonPath().getString("email");
	     String sessionKey = response.jsonPath().getString("sessionKey");
	     String deviceId = response.jsonPath().getString("deviceId");
	     String signature = response.jsonPath().getString("signature");

	     assertNotNull(signature, "Signature is missing");
	     assertNotNull(sessionKey, "sessionkey is missing from the response");
	     assertNotNull(deviceId, "deviceid is missing from the response");
	     assertNotNull(responseEmail, "email in reponse is missing");

	     assertFalse(signature.isEmpty(), "Signature is empty");
	     assertFalse(deviceId.isEmpty(), "Device ID is empty");
	     assertFalse(sessionKey.isEmpty(), "session key is empty");
	     assertFalse(responseEmail.isEmpty(), "email is empty");
	     
	     //assert device and email is equal with request
	     assertEquals(requestdevice,deviceId);
	     assertEquals(email,responseEmail);
	     
	     
	     
	     //verify signature
	     //matching response signature with calculated hash
	     Map<String, Object> fields = new LinkedHashMap<>();
	     fields.put("deviceId", deviceId);
	     fields.put("sessionKey", sessionKey);
	     fields.put("email", responseEmail);
	     

	     String partialJson = objectMapper.writeValueAsString(fields);
	     String partialSignature = signatureCreate.generateHMACSHA256(partialJson, secretKey);
	     assertEquals(signature, partialSignature);
	     
	     
	     
//	    // assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
	    System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
	     
	 }
}

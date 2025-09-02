package OTP;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class dormantVerify {
	 String baseURI = "https://visitor0.moco.com.np/visitor";
	 String secretKey = "3b26ced1ba16f72dbd637ed49b94f70be53715e13fbbe8414a4263441ae753c1";
	 String email = "vivek@moco.com.np";
     String requestDeviceId = "visitor-app-device";
     String plain_otp = "585905";
     String token = "KcMaTtUYpLvN8ltsiuhgS0gmcgubnC1dZ6Nhq7h3yqM";
     String pushToken = "oipoi";
     
     @Test
     public void verifyAccountwithoutdeviceId() throws Exception {
    	 ObjectMapper objectMapper = new ObjectMapper();
         
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);

         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id", "")
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
                 .extract().response();
         

         // Extracting and asserting response values
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         // check if it is null
         assertNotNull(code, "code is missing");
         assertNotNull(description, "description is missing from the response");
         
         //check if it is empty
         assertFalse(code.isEmpty(), "code is empty");
         assertFalse(description.isEmpty(), "description is empty");
         
         //assert code and description
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
     	
     }
     
     @Test
 	public void verifyprofilewithoutLocation() throws Exception {
 		ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
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
 	public void verifyprofilewithoutdevice() throws Exception {
 		ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id","")
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
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
     public void verifyprofilewithoutUserAgent() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
                 .extract().response();
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
     }
     
     @Test
     public void VerifyprofilewithInvalidLocation() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12@12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(422)
                 .log().all()
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
     public void verifyprofilewithInvaliddevice() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id","$$")
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(422)
                 .log().all()
                 .extract().response();
         
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid device Id found.");
     }
     
     @Test
     public void verifyprofilewithInvalidUserAgent() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelA.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(422)
                 .log().all()
                 .extract().response();
         
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid user agent found.");
     }
     
     @Test
     public void verifyprofilewithInvalidEmailFormat() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivekmoco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(422)
                 .log().all()
                 .extract().response();
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid Data.");
     }
     
     
 
     @Test
     public void verifyprofilewithInvalidRequestTimestamp() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp","25:56:58 11:55:55");
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(422)
                 .log().all()
                 .extract().response();
         
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid Data.");
     }
     @Test
     public void verifyprofilewithoutRequestTimestamp() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp","");
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
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
     public void verifyprofilewithoutOtp() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", "");
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
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
     public void verifyprofilewithexpiredOtp() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", "270873");
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(200)
                 .log().all()
                 .extract().response();
         
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         String status = response.jsonPath().getString("status");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"VST_PROFILE_OTP_FAILED");
         assertEquals(description,"Account activation failed due to invalid OTP.");
         assertEquals(status,"FAIL");
     }
     @Test
     public void verifyprofilewithincorrectOtp() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", "192292");
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(200)
                 .log().all()
                 .extract().response();
         
         String code = response.jsonPath().getString("code");
       String description = response.jsonPath().getString("description");
     String status = response.jsonPath().getString("status");
       
       assertNotNull(code, "code is missing from the response");
       assertFalse(code.isEmpty(), "code is empty in the response");
       assertNotNull(description, "description is missing from the response");
       assertFalse(description.isEmpty(), "description is empty in the response");
      
       
       assertEquals(code,"VST_PROFILE_OTP_FAILED");
       assertEquals(description,"Account activation failed due to invalid OTP.");
      // assertEquals(status,"FAIL");
     }
     @Test
     public void verifyprofilewithInvalidOtp() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", "111111");
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(200)
                 .log().all()
                 .extract().response();
         
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         //String signature = response.jsonPath().getString("signature");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"VST_PROFILE_OTP_FAILED");
         assertEquals(description,"Account activation failed due to invalid OTP.");
     }
     @Test
     public void verifyprofilewithIncorrectToken() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", "mm");
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(200)
                 .log().all()
                 .extract().response();
         
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         //String signature = response.jsonPath().getString("signature");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"VST_PROFILE_OTP_FAILED");
         assertEquals(description,"Account activation failed due to invalid OTP.");
     }
 //    
     @Test
     public void verifyprofilewithoutToken() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", "");
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
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
     public void verifyprofilewithoutSignature() throws Exception {
     	//ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
        // String data = objectMapper.writeValueAsString(jsonBody);
        // String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", "");
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
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
     public void verifyprofilewithInvalidSignature() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,"kk");
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(401)
                 .log().all()
                 .extract().response();
         
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
     public void verifyprofilewithoutpushtoken() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", "");
         
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
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(400)
                 .log().all()
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
//     @Test
//     public void verifyprofilewithDisabledUser() throws Exception {
//     	ObjectMapper objectMapper = new ObjectMapper();
//         String email = "vivek@moco.com.np";
//         Map<String, Object> credentials = new HashMap<>();
//         credentials.put("email", email);
//
//         Map<String, Object> jsonBody = new LinkedHashMap<>();
//         jsonBody.put("credentials", credentials);
//         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
//         jsonBody.put("otp", plain_otp);
//         jsonBody.put("token", token);
//         jsonBody.put("pushToken", pushToken);
//         
//         // Generate signature
//         String data = objectMapper.writeValueAsString(jsonBody);
//         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//         
//      // Add signature
//         jsonBody.put("signature", requestSignature);
//         
//         Response response = given()
//                 .baseUri(baseURI)
//                 .header("X-GEO-Location", "12,12")
//                 .header("X-Device-Id",requestDeviceId)
//                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                 .contentType("application/json")
//                 .body(jsonBody)
//             .when()
//                 .post("/profile/otp/verify")
//             .then()
//                 .statusCode(409)
//                 .log().all()
//                 .extract().response();
//         
//         String code = response.jsonPath().getString("code");
//         String description = response.jsonPath().getString("description");
//         //String signature = response.jsonPath().getString("signature");
//         
//         assertNotNull(code, "code is missing from the response");
//         assertFalse(code.isEmpty(), "code is empty in the response");
//         assertNotNull(description, "description is missing from the response");
//         assertFalse(description.isEmpty(), "description is empty in the response");
//        
//         
//         assertEquals(code,"VST_profile_VERIFICATION_DUPLICATE");
//         assertEquals(description,"profile already exists.");
//     }
     
     @Test
     public void verifyprofilewithValidCredentials() throws Exception {
     	ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         Map<String, Object> credentials = new HashMap<>();
         credentials.put("email", email);

         Map<String, Object> jsonBody = new LinkedHashMap<>();
         jsonBody.put("credentials", credentials);
         jsonBody.put("requestTimestamp",signatureCreate.generateTimestamp());
         jsonBody.put("otp", plain_otp);
         jsonBody.put("token", token);
         jsonBody.put("pushToken", pushToken);
         
         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
         
      // Add signature
         jsonBody.put("signature", requestSignature);
         
         Response response = given()
                 .baseUri(baseURI)
                 .header("X-GEO-Location", "12,12")
                 .header("X-Device-Id",requestDeviceId)
                 .header("User-Agent", "NepalTravelApp/1.0.0 android")
                 .contentType("application/json")
                 .body(jsonBody)
             .when()
                 .post("/profile/otp/verify")
             .then()
                 .statusCode(200)
                 .log().all()
                 .extract().response();
         
         String code = response.jsonPath().getString("code");
         String description = response.jsonPath().getString("description");
         //String signature = response.jsonPath().getString("signature");
         
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
        
         
         assertEquals(code,"GNR_OK");
         assertEquals(description,"OTP Verified and account activated successfully.");
     }
}

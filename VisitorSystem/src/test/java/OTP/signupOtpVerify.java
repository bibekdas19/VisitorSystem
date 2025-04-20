package OTP;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

public class signupOtpVerify {
	String baseURI = "https://visitor0.moco.com.np/visitor";
    String secretKey;
    String code;
    String token;

    @BeforeClass
    public void getSecretKeyAndSignup() throws Exception {
        // Get secret key
        Response keyResponse = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .when()
                .get("/key")
            .then()
                .statusCode(200)
                .extract().response();

        secretKey = keyResponse.jsonPath().getString("signOnKey");
        assertNotNull(secretKey, "Secret key is null!");

        // Signup to get OTP and token
        String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response signupResponse = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        code = signupResponse.jsonPath().getString("code");
        token = signupResponse.jsonPath().getString("token");
    }
    @Test
    public void VerifyOtpwithValidCredentials() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String pin = "123456";
        String data = email + requestTimestamp + code + pin + token;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", code);
        jsonBody.put("pin",pin);
        jsonBody.put("token", token);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String token = response.jsonPath().getString("deviceId");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(token, "Device ID is missing from the response");
        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(token.isEmpty(), "Device ID is empty");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
    }
    
    @Test
    public void verifySignUpOtpwithoutDeviceId() throws Exception {
    	String email = "vivek@moco.com.np"; 
        String requestTimestamp = "2025-04-20 10:22:00";
        String pin = "123456";
        String data = email + requestTimestamp + code + pin + token;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", code);
        jsonBody.put("pin",pin);
        jsonBody.put("token", token);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        String code = response.jsonPath().getString("code");
        String signature = response.jsonPath().getString("signature");
        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "Code is missing");
        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "Code is empty");
    	
    }
    
    @Test
    public void verifySignUpOtpwithoutLocation() throws Exception{
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String pin = "123456";
        String data = email + requestTimestamp + code + pin + token;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", code);
        jsonBody.put("pin",pin);
        jsonBody.put("token", token);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        String code = response.jsonPath().getString("code");
        String signature = response.jsonPath().getString("signature");
        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "Code is missing");
        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "Code is empty");

    }
    
    @Test
    public void verifySignUpwithoutUserAgent() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String pin = "123456";
        String data = email + requestTimestamp + code + pin + token;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", code);
        jsonBody.put("pin",pin);
        jsonBody.put("token", token);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        String code = response.jsonPath().getString("code");
        String signature = response.jsonPath().getString("signature");
        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "Code is missing");
        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        

    }
    
    @Test
    public void VerifySignupOtpwithoutemail() throws Exception{
    	String email = "";
        String requestTimestamp = "2025-04-20 10:22:00";
        String pin = "123456";
        String data = email + requestTimestamp + code + pin + token;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", "");
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", code);
        jsonBody.put("pin",pin);
        jsonBody.put("token", token);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        String code = response.jsonPath().getString("code");
        String signature = response.jsonPath().getString("signature");
        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "Code is missing");
        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "Code is empty");
    }
    
  @Test
  public void VerifySignUpOtpwithoutTimestamp() throws Exception{
	  String email = "vivek@moco.com.np";
      String requestTimestamp = "";
      String pin = "123456";
      String data = email + requestTimestamp + code + pin + token;

      String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

      Map<String, Object> jsonBody = new HashMap<>();
      jsonBody.put("email", email);
      jsonBody.put("requestTimestamp", "");
      jsonBody.put("otp", code);
      jsonBody.put("pin",pin);
      jsonBody.put("token", token);
      jsonBody.put("signature", requestSignature);

      Response response = given()
              .baseUri(baseURI)
              .header("X-GEO-Location", "12,12")
              .header("X-Device-Id", "")
              .header("User-Agent", "NepalTravelApp/1.0.0 android")
              .contentType("application/json")
              .body(jsonBody)
          .when()
              .post("/signup/otp")
          .then()
              .statusCode(400)
              .log().all()
              .extract().response();
      String code = response.jsonPath().getString("code");
      String signature = response.jsonPath().getString("signature");
      assertNotNull(signature, "Signature is missing");
      assertNotNull(code, "Code is missing");
      assertFalse(signature.isEmpty(), "Signature is empty");
      assertFalse(code.isEmpty(), "Code is empty");
  }
  
 @Test
 public void VerifySignUpOtpwithoutOtp() throws Exception {
	 String email = "vivek@moco.com.np";
     String requestTimestamp = "2025-04-20 15:15:15";
     String pin = "123456";
     String data = email + requestTimestamp + code + pin + token;

     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", "");
     jsonBody.put("pin",pin);
     jsonBody.put("token", token);
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", "")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp")
         .then()
             .statusCode(400)
             .log().all()
             .extract().response();
     String code = response.jsonPath().getString("code");
     String signature = response.jsonPath().getString("signature");
     assertNotNull(signature, "Signature is missing");
     assertNotNull(code, "Code is missing");
     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(code.isEmpty(), "Code is empty");
     
 }
 
 @Test
 public void VerifySignUpOtpwithoutpin() throws Exception {
	 String email = "vivek@moco.com.np";
     String requestTimestamp = "2025-04-20 15:15:15";
     String pin = "";
     String data = email + requestTimestamp + code + pin + token;

     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", code);
     jsonBody.put("pin",pin);
     jsonBody.put("token", token);
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", "")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp")
         .then()
             .statusCode(400)
             .log().all()
             .extract().response();
     String code = response.jsonPath().getString("code");
     String signature = response.jsonPath().getString("signature");
     assertNotNull(signature, "Signature is missing");
     assertNotNull(code, "Code is missing");
     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(code.isEmpty(), "Code is empty");
	 
 }
 
 @Test
 public void VerifySignUpwithouttoken() throws Exception {
	 String email = "vivek@moco.com.np";
     String requestTimestamp = "2025-04-20 15:15:15";
     String pin = "";
     String data = email + requestTimestamp + code + pin + token;

     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", code);
     jsonBody.put("pin",pin);
     jsonBody.put("token", "");
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", "")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp")
         .then()
             .statusCode(400)
             .log().all()
             .extract().response();
     String code = response.jsonPath().getString("code");
     String signature = response.jsonPath().getString("signature");
     assertNotNull(signature, "Signature is missing");
     assertNotNull(code, "Code is missing");
     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(code.isEmpty(), "Code is empty");
 }
 
 @Test
 public void VerifySignUpwithoutSignature() throws Exception {
	 String email = "vivek@moco.com.np";
     String requestTimestamp = "2025-04-20 15:15:15";
     String pin = "123456";
     //String data = email + requestTimestamp + code + pin + token;

     //String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", code);
     jsonBody.put("pin",pin);
     jsonBody.put("token", token);
     jsonBody.put("signature", "");

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", "")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp")
         .then()
             .statusCode(400)
             .log().all()
             .extract().response();
     String code = response.jsonPath().getString("code");
     String signature = response.jsonPath().getString("signature");
     assertNotNull(signature, "Signature is missing");
     assertNotNull(code, "Code is missing");
     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(code.isEmpty(), "Code is empty");
 }
 
 @Test
 public void VerifySignUpwithInvalidEmail() throws Exception {
	 String email = "vivek@mocoa..com.np";
     String requestTimestamp = "2025-04-20 15:15:15";
     String pin = "123456";
     String data = email + requestTimestamp + code + pin + token;

     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", code);
     jsonBody.put("pin",pin);
     jsonBody.put("token", token);
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", "")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp")
         .then()
             .statusCode(422)
             .log().all()
             .extract().response();
     String code = response.jsonPath().getString("code");
     String signature = response.jsonPath().getString("signature");
     assertNotNull(signature, "Signature is missing");
     assertNotNull(code, "Code is missing");
     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(code.isEmpty(), "Code is empty");
 }
 @Test
 public void VerifySignupwithInvalidTimestamp() throws Exception {
	 String email = "vivek@moco.com.np";
     String requestTimestamp = "2025-04-20 25:15:15";
     String pin = "123456";
     String data = email + requestTimestamp + code + pin + token;

     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", code);
     jsonBody.put("pin",pin);
     jsonBody.put("token", token);
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", "")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp")
         .then()
             .statusCode(422)
             .log().all()
             .extract().response();
     String code = response.jsonPath().getString("code");
     String signature = response.jsonPath().getString("signature");
     assertNotNull(signature, "Signature is missing");
     assertNotNull(code, "Code is missing");
     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(code.isEmpty(), "Code is empty");
	 
 }
 
@Test
public void VerifySignUpwithInvalidOtp() throws Exception {
	String email = "vivek@moco.com.np";
    String requestTimestamp = "2025-04-20 25:15:15";
    String pin = "123456";
    String data = email + requestTimestamp + "aasa" + pin + token;

    String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

    Map<String, Object> jsonBody = new HashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
    jsonBody.put("otp",code);
    jsonBody.put("pin",pin);
    jsonBody.put("token", token);
    jsonBody.put("signature", requestSignature);

    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("/signup/otp")
        .then()
            .statusCode(422)
            .log().all()
            .extract().response();
    String code = response.jsonPath().getString("code");
    String signature = response.jsonPath().getString("signature");
    assertNotNull(signature, "Signature is missing");
    assertNotNull(code, "Code is missing");
    assertFalse(signature.isEmpty(), "Signature is empty");
    assertFalse(code.isEmpty(), "Code is empty");
	
}

@Test
public void VerifySignUpwithInvalidPin() throws Exception {
	String email = "vivek@moco.com.np";
    String requestTimestamp = "2025-04-20 25:15:15";
    String pin = "111250";
    String data = email + requestTimestamp + code + pin + token;

    String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

    Map<String, Object> jsonBody = new HashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
    jsonBody.put("otp", code);
    jsonBody.put("pin",pin);
    jsonBody.put("token", token);
    jsonBody.put("signature", requestSignature);

    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("/signup/otp")
        .then()
            .statusCode(422)
            .log().all()
            .extract().response();
    String code = response.jsonPath().getString("code");
    String signature = response.jsonPath().getString("signature");
    assertNotNull(signature, "Signature is missing");
    assertNotNull(code, "Code is missing");
    assertFalse(signature.isEmpty(), "Signature is empty");
    assertFalse(code.isEmpty(), "Code is empty");
}
 @Test
 public void VerifySignUpwithInvalidToken() throws Exception {
	 String email = "vivek@moco.com.np";
     String requestTimestamp = "2025-04-20 25:15:15";
     String pin = "123456";
     String data = email + requestTimestamp + code + pin + "sasad";

     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", code);
     jsonBody.put("pin",pin);
     jsonBody.put("token", "sasad");
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", "")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp")
         .then()
             .statusCode(422)
             .log().all()
             .extract().response();
     String code = response.jsonPath().getString("code");
     String signature = response.jsonPath().getString("signature");
     assertNotNull(signature, "Signature is missing");
     assertNotNull(code, "Code is missing");
     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(code.isEmpty(), "Code is empty");
 }
 
 @Test
 public void VerifySignUpwithWrongSignature() throws Exception {
	 String email = "vivek@moco.com.np";
     String requestTimestamp = "2025-04-20 25:15:15";
     String pin = "123456";
    // String data = email + requestTimestamp + code + pin + token;

     //String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", code);
     jsonBody.put("pin",pin);
     jsonBody.put("token", token);
     jsonBody.put("signature", "saseegqqqwf");

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", "")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp")
         .then()
             .statusCode(401)
             .log().all()
             .extract().response();
     String code = response.jsonPath().getString("code");
     String signature = response.jsonPath().getString("signature");
     assertNotNull(signature, "Signature is missing");
     assertNotNull(code, "Code is missing");
     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(code.isEmpty(), "Code is empty");
 }
 


}

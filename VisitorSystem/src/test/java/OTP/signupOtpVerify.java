package OTP;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

public class signupOtpVerify {
	String baseURI = "https://visitor0.moco.com.np/visitor";
    String secretKey;
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
        ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestTimestamp = signatureCreate.generateTimestamp();
   
     // Prepare payload without signature
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        // Add signature
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
        token = signupResponse.jsonPath().getString("token");
    }
    
    @Test
    public void VerifyOtpwithValidCredentials() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
    	String email = "vivek@moco.com.np";
        String requestTimestamp = signatureCreate.generateTimestamp();
        String pin = "152986";
        String requestdevice = "3efe6bbeb55f4411";
        
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", "");
        jsonBody.put("pin", pin);
        jsonBody.put("token", token);
        

     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

     // Add signature
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp/verify")
            .then()
                .statusCode(201)
                .log().all()
                .extract().response();
        

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
        
    }

    
    @Test
    public void verifySignUpOtpwithoutDeviceId() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
    	String email = "vivek@moco.com.np";
        String requestTimestamp = signatureCreate.generateTimestamp();
        String pin = "152986";
        String requestdevice = "3efe6bbeb55f4411";
        
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", "");
        jsonBody.put("pin", pin);
        jsonBody.put("token", token);
        

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
                .post("/signup/otp/verify")
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
    public void verifySignUpOtpwithoutLocation() throws Exception{
    	ObjectMapper objectMapper = new ObjectMapper();
    	String email = "vivek@moco.com.np";
        String requestTimestamp = signatureCreate.generateTimestamp();
        String pin = "152986";
        String requestdevice = "3efe6bbeb55f4411";
        
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", "");
        jsonBody.put("pin", pin);
        jsonBody.put("token", token);
        

     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

     // Add signature
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp/verify")
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
    public void verifySignUpwithoutUserAgent() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
    	String email = "vivek@moco.com.np";
        String requestTimestamp = signatureCreate.generateTimestamp();
        String pin = "152986";
        String requestdevice = "3efe6bbeb55f4411";
        
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", "");
        jsonBody.put("pin", pin);
        jsonBody.put("token", token);
        

     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

     // Add signature
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp/verify")
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
    public void VerifySignupOtpwithoutemail() throws Exception{
    	ObjectMapper objectMapper = new ObjectMapper();
    	String email = "";
        String requestTimestamp = signatureCreate.generateTimestamp();
        String pin = "152986";
        String requestdevice = "3efe6bbeb55f4411";
        
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("otp", "");
        jsonBody.put("pin", pin);
        jsonBody.put("token", token);
        

     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

     // Add signature
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp/verify")
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
//    
//  @Test
//  public void VerifySignUpOtpwithoutTimestamp() throws Exception{
//	  String email = "vivek@moco.com.np";
//      String requestTimestamp = "";
//      String pin = "123456";
//      String data = email + requestTimestamp + code + pin + token;
//
//      String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//      Map<String, Object> jsonBody = new HashMap<>();
//      jsonBody.put("email", email);
//      jsonBody.put("requestTimestamp", "");
//      jsonBody.put("otp", code);
//      jsonBody.put("pin",pin);
//      jsonBody.put("token", token);
//      jsonBody.put("signature", requestSignature);
//
//      Response response = given()
//              .baseUri(baseURI)
//              .header("X-GEO-Location", "12,12")
//              .header("X-Device-Id", "")
//              .header("User-Agent", "NepalTravelApp/1.0.0 android")
//              .contentType("application/json")
//              .body(jsonBody)
//          .when()
//              .post("/signup/otp/verify")
//          .then()
//              .statusCode(400)
//              .log().all()
//              .extract().response();
//      String code = response.jsonPath().getString("code");
//      String signature = response.jsonPath().getString("signature");
//      assertNotNull(signature, "Signature is missing");
//      assertNotNull(code, "Code is missing");
//      assertFalse(signature.isEmpty(), "Signature is empty");
//      assertFalse(code.isEmpty(), "Code is empty");
//  }
//  
// @Test
// public void VerifySignUpOtpwithoutOtp() throws Exception {
//	 String email = "vivek@moco.com.np";
//     String requestTimestamp = "2025-04-20 15:15:15";
//     String pin = "123456";
//     String data = email + requestTimestamp + code + pin + token;
//
//     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//     Map<String, Object> jsonBody = new HashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", "");
//     jsonBody.put("pin",pin);
//     jsonBody.put("token", token);
//     jsonBody.put("signature", requestSignature);
//
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id", "")
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(400)
//             .log().all()
//             .extract().response();
//     String code = response.jsonPath().getString("code");
//     String signature = response.jsonPath().getString("signature");
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "Code is missing");
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "Code is empty");
//     
// }
// 
// @Test
// public void VerifySignUpOtpwithoutpin() throws Exception {
//	 String email = "vivek@moco.com.np";
//     String requestTimestamp = "2025-04-20 15:15:15";
//     String pin = "";
//     String data = email + requestTimestamp + code + pin + token;
//
//     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//     Map<String, Object> jsonBody = new HashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", code);
//     jsonBody.put("pin",pin);
//     jsonBody.put("token", token);
//     jsonBody.put("signature", requestSignature);
//
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id", "")
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(400)
//             .log().all()
//             .extract().response();
//     String code = response.jsonPath().getString("code");
//     String signature = response.jsonPath().getString("signature");
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "Code is missing");
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "Code is empty");
//	 
// }
// 
// @Test
// public void VerifySignUpwithouttoken() throws Exception {
//	 String email = "vivek@moco.com.np";
//     String requestTimestamp = "2025-04-20 15:15:15";
//     String pin = "";
//     String data = email + requestTimestamp + code + pin + token;
//
//     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//     Map<String, Object> jsonBody = new HashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", code);
//     jsonBody.put("pin",pin);
//     jsonBody.put("token", "");
//     jsonBody.put("signature", requestSignature);
//
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id", "")
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(400)
//             .log().all()
//             .extract().response();
//     String code = response.jsonPath().getString("code");
//     String signature = response.jsonPath().getString("signature");
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "Code is missing");
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "Code is empty");
// }
// 
// @Test
// public void VerifySignUpwithoutSignature() throws Exception {
//	 String email = "vivek@moco.com.np";
//     String requestTimestamp = "2025-04-20 15:15:15";
//     String pin = "123456";
//     //String data = email + requestTimestamp + code + pin + token;
//
//     //String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//     Map<String, Object> jsonBody = new HashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", code);
//     jsonBody.put("pin",pin);
//     jsonBody.put("token", token);
//     jsonBody.put("signature", "");
//
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id", "")
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(400)
//             .log().all()
//             .extract().response();
//     String code = response.jsonPath().getString("code");
//     String signature = response.jsonPath().getString("signature");
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "Code is missing");
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "Code is empty");
// }
// 
// @Test
// public void VerifySignUpwithInvalidEmail() throws Exception {
//	 String email = "vivek@mocoa..com.np";
//     String requestTimestamp = "2025-04-20 15:15:15";
//     String pin = "123456";
//     String data = email + requestTimestamp + code + pin + token;
//
//     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//     Map<String, Object> jsonBody = new HashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", code);
//     jsonBody.put("pin",pin);
//     jsonBody.put("token", token);
//     jsonBody.put("signature", requestSignature);
//
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id", "")
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(422)
//             .log().all()
//             .extract().response();
//     String code = response.jsonPath().getString("code");
//     String signature = response.jsonPath().getString("signature");
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "Code is missing");
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "Code is empty");
// }
// @Test
// public void VerifySignupwithInvalidTimestamp() throws Exception {
//	 String email = "vivek@moco.com.np";
//     String requestTimestamp = "2025-04-20 25:15:15";
//     String pin = "123456";
//     String data = email + requestTimestamp + code + pin + token;
//
//     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//     Map<String, Object> jsonBody = new HashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", code);
//     jsonBody.put("pin",pin);
//     jsonBody.put("token", token);
//     jsonBody.put("signature", requestSignature);
//
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id", "")
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(422)
//             .log().all()
//             .extract().response();
//     String code = response.jsonPath().getString("code");
//     String signature = response.jsonPath().getString("signature");
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "Code is missing");
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "Code is empty");
//	 
// }
// 
//@Test
//public void VerifySignUpwithInvalidOtp() throws Exception {
//	String email = "vivek@moco.com.np";
//    String requestTimestamp = "2025-04-20 25:15:15";
//    String pin = "123456";
//    String data = email + requestTimestamp + "aasa" + pin + token;
//
//    String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//    Map<String, Object> jsonBody = new HashMap<>();
//    jsonBody.put("email", email);
//    jsonBody.put("requestTimestamp", requestTimestamp);
//    jsonBody.put("otp",code);
//    jsonBody.put("pin",pin);
//    jsonBody.put("token", token);
//    jsonBody.put("signature", requestSignature);
//
//    Response response = given()
//            .baseUri(baseURI)
//            .header("X-GEO-Location", "12,12")
//            .header("X-Device-Id", "")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//            .contentType("application/json")
//            .body(jsonBody)
//        .when()
//            .post("/signup/otp/verify")
//        .then()
//            .statusCode(422)
//            .log().all()
//            .extract().response();
//    String code = response.jsonPath().getString("code");
//    String signature = response.jsonPath().getString("signature");
//    assertNotNull(signature, "Signature is missing");
//    assertNotNull(code, "Code is missing");
//    assertFalse(signature.isEmpty(), "Signature is empty");
//    assertFalse(code.isEmpty(), "Code is empty");
//	
//}
//
//@Test
//public void VerifySignUpwithInvalidPin() throws Exception {
//	String email = "vivek@moco.com.np";
//    String requestTimestamp = "2025-04-20 25:15:15";
//    String pin = "111250";
//    String data = email + requestTimestamp + code + pin + token;
//
//    String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//    Map<String, Object> jsonBody = new HashMap<>();
//    jsonBody.put("email", email);
//    jsonBody.put("requestTimestamp", requestTimestamp);
//    jsonBody.put("otp", code);
//    jsonBody.put("pin",pin);
//    jsonBody.put("token", token);
//    jsonBody.put("signature", requestSignature);
//
//    Response response = given()
//            .baseUri(baseURI)
//            .header("X-GEO-Location", "12,12")
//            .header("X-Device-Id", "")
//            .header("User-Agent", "NepalTravelApp/1.0.0 android")
//            .contentType("application/json")
//            .body(jsonBody)
//        .when()
//            .post("/signup/otp/verify")
//        .then()
//            .statusCode(422)
//            .log().all()
//            .extract().response();
//    String code = response.jsonPath().getString("code");
//    String signature = response.jsonPath().getString("signature");
//    assertNotNull(signature, "Signature is missing");
//    assertNotNull(code, "Code is missing");
//    assertFalse(signature.isEmpty(), "Signature is empty");
//    assertFalse(code.isEmpty(), "Code is empty");
//}
// @Test
// public void VerifySignUpwithInvalidToken() throws Exception {
//	 String email = "vivek@moco.com.np";
//     String requestTimestamp = "2025-04-20 25:15:15";
//     String pin = "123456";
//     String data = email + requestTimestamp + code + pin + "sasad";
//
//     String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//     Map<String, Object> jsonBody = new HashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", code);
//     jsonBody.put("pin",pin);
//     jsonBody.put("token", "sasad");
//     jsonBody.put("signature", requestSignature);
//
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id", "")
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(422)
//             .log().all()
//             .extract().response();
//     String code = response.jsonPath().getString("code");
//     String signature = response.jsonPath().getString("signature");
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "Code is missing");
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "Code is empty");
// }
// 
// @Test
// public void VerifySignUpwithWrongSignature() throws Exception {
//	 String email = "vivek@moco.com.np";
//     String requestTimestamp = "2025-04-20 25:15:15";
//     String pin = "123456";
//    // String data = email + requestTimestamp + code + pin + token;
//
//     //String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//     Map<String, Object> jsonBody = new HashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", code);
//     jsonBody.put("pin",pin);
//     jsonBody.put("token", token);
//     jsonBody.put("signature", "saseegqqqwf");
//
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id", "")
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(401)
//             .log().all()
//             .extract().response();
//     String code = response.jsonPath().getString("code");
//     String signature = response.jsonPath().getString("signature");
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "Code is missing");
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "Code is empty");
// }
// 
//
//
//}

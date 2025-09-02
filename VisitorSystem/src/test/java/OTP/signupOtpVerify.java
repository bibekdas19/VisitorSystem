package OTP;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

public class signupOtpVerify {
	String baseURI = "https://visitor0.moco.com.np/visitor";
    String secretKey = "dc6673a393c761f716a980c70fcaf99da7adba77c37a4e37dce347a86c3f612d";
    String token = "wvD1iQmAVeCyOCI9VDGQTfZuxpP0J89u_QoSZ9yWd9o";
    String email = "vivek@moco.com.np";
    String requestTimestamp = signatureCreate.generateTimestamp();
    String plain_pin = "123654";
    String plain_otp = "843231";
    String requestdevice = "visitor-app-device";
    String pushToken = "abcdefg:7^";
    int deviceScreenWidth = 325;
    int deviceScreenHeight = 485;
    int deviceColorDepth = 6;
    String deviceModel = "CPH256";

    @Test
    public void verifySignUpOtpwithoutDeviceId() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        //String requestdevice = "3efe6bbeb55f4411";
        
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
       
        //encryption with AES256 algorithm0
        String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
        jsonBody.put("otp", otp);
        String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        jsonBody.put("pin", pin);
        jsonBody.put("pushToken",pushToken);
        jsonBody.put("deviceModel", deviceModel);
        jsonBody.put("deviceColorDepth", deviceColorDepth);
        jsonBody.put("deviceScreenHeight", deviceScreenHeight);
        jsonBody.put("deviceScreenWidth", deviceScreenWidth);
        
        //placing token from signup API
        jsonBody.put("token", token);
        

     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

     // Add signature
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);

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
        
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
       
        //encryption with AES256 algorithm0
        String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
        jsonBody.put("otp", otp);
        String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        jsonBody.put("pin", pin);
        jsonBody.put("pushToken",pushToken);
        jsonBody.put("deviceModel", deviceModel);
        jsonBody.put("deviceColorDepth", deviceColorDepth);
        jsonBody.put("deviceScreenHeight", deviceScreenHeight);
        jsonBody.put("deviceScreenWidth", deviceScreenWidth);
        
        //placing token from signup API
        jsonBody.put("token", token);
        

     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

     // Add signature
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "")
                .header("X-Device-Id",requestdevice)
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
    	
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
       
        //encryption with AES256 algorithm0
        String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
        jsonBody.put("otp", otp);
        String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        jsonBody.put("pin", pin);
        jsonBody.put("pushToken",pushToken);
        jsonBody.put("deviceModel", deviceModel);
        jsonBody.put("deviceColorDepth", deviceColorDepth);
        jsonBody.put("deviceScreenHeight", deviceScreenHeight);
        jsonBody.put("deviceScreenWidth", deviceScreenWidth);
        
        //placing token from signup API
        jsonBody.put("token", token);
        

     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

     // Add signature
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id",requestdevice)
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
        
        
        String plain_otp = "443062";
        String requestdevice = "3efe6bbeb55f4411";
        
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
       
        //encryption with AES256 algorithm0
        String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
        jsonBody.put("otp", otp);
        String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
        jsonBody.put("pin", pin);
        jsonBody.put("pushToken",pushToken);
        jsonBody.put("deviceModel", deviceModel);
        jsonBody.put("deviceColorDepth", deviceColorDepth);
        jsonBody.put("deviceScreenHeight", deviceScreenHeight);
        jsonBody.put("deviceScreenWidth", deviceScreenWidth);
        
        //placing token from signup API
        jsonBody.put("token", token);
        

     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

     // Add signature
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id",requestdevice)
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
  @Test
  public void VerifySignUpOtpwithoutTimestamp() throws Exception{
	  ObjectMapper objectMapper = new ObjectMapper();
  	
      String requestTimestamp = "";
      
      String plain_otp = "443062";
      String requestdevice = "3efe6bbeb55f4411";
      
      Map<String, Object> jsonBody = new HashMap<>();
      jsonBody.put("email", email);
      jsonBody.put("requestTimestamp", requestTimestamp);
     
      //encryption with AES256 algorithm0
      String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
      jsonBody.put("otp", otp);
      String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
      jsonBody.put("pin", pin);
      jsonBody.put("pushToken",pushToken);
      jsonBody.put("deviceModel", deviceModel);
      jsonBody.put("deviceColorDepth", deviceColorDepth);
      jsonBody.put("deviceScreenHeight", deviceScreenHeight);
      jsonBody.put("deviceScreenWidth", deviceScreenWidth);
      
      //placing token from signup API
      jsonBody.put("token", token);
      

   // Generate signature
      String data = objectMapper.writeValueAsString(jsonBody);
      String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

   // Add signature
      jsonBody.put("signature", requestSignature);

    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id",requestdevice)
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
 @Test
 public void VerifySignUpOtpwithoutOtp() throws Exception {
	 ObjectMapper objectMapper = new ObjectMapper();
 	
     
     
     String plain_otp = "";
     String requestdevice = "3efe6bbeb55f4411";
     
     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
    
     //encryption with AES256 algorithm0
     String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
     jsonBody.put("otp", otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin", pin);
     jsonBody.put("pushToken",pushToken);
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     
     //placing token from signup API
     jsonBody.put("token", token);
     

  // Generate signature
     String data = objectMapper.writeValueAsString(jsonBody);
     String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

  // Add signature
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id",requestdevice)
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
 @Test
 public void VerifySignUpOtpwithoutpin() throws Exception {
	ObjectMapper objectMapper = new ObjectMapper();
 	
    
    
    String plain_otp = "";
    String requestdevice = "3efe6bbeb55f4411";
    
    Map<String, Object> jsonBody = new HashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
   
    //encryption with AES256 algorithm0
    String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
    jsonBody.put("otp", otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin", pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel", deviceModel);
    jsonBody.put("deviceColorDepth", deviceColorDepth);
    jsonBody.put("deviceScreenHeight", deviceScreenHeight);
    jsonBody.put("deviceScreenWidth", deviceScreenWidth);
    
    //placing token from signup API
    jsonBody.put("token", token);
    

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

 // Add signature
    jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id",requestdevice)
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
 @Test
 public void VerifySignUpwithouttoken() throws Exception {
	 ObjectMapper objectMapper = new ObjectMapper();
 	
     
     
     String plain_otp = "443062";
     String requestdevice = "3efe6bbeb55f4411";
     
     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
    
     //encryption with AES256 algorithm0
     String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
     jsonBody.put("otp", otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin", pin);
     jsonBody.put("pushToken",pushToken);
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     
     //placing token from signup API
     jsonBody.put("token", "");
     

  // Generate signature
     String data = objectMapper.writeValueAsString(jsonBody);
     String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

  // Add signature
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id",requestdevice)
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
 public void VerifySignUpwithoutSignature() throws Exception {
	// ObjectMapper objectMapper = new ObjectMapper();
 	 
     
     
     String plain_otp = "443062";
     String requestdevice = "3efe6bbeb55f4411";
     
     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
    
     //encryption with AES256 algorithm0
     String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
     jsonBody.put("otp", otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin", pin);
     jsonBody.put("pushToken",pushToken);
     
     //placing token from signup API
     jsonBody.put("token", token);
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     

  // Generate signature
     //String data = objectMapper.writeValueAsString(jsonBody);
     //String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

  // Add signature
     jsonBody.put("signature", "");

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", requestdevice)
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
 @Test
 public void VerifySignUpwithInvalidEmail() throws Exception {
	 ObjectMapper objectMapper = new ObjectMapper();
 	String email = "vivekmoco.com.np";
     
     
     String plain_otp = "443062";
     String requestdevice = "3efe6bbeb55f4411";
     
     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
    
     //encryption with AES256 algorithm0
    // String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
     jsonBody.put("otp", plain_otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin", pin);
     jsonBody.put("pushToken",pushToken);
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     
     //placing token from signup API
     jsonBody.put("token", token);
     

  // Generate signature
     String data = objectMapper.writeValueAsString(jsonBody);
     String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

  // Add signature
     jsonBody.put("signature", requestSignature);
     
     System.out.println(jsonBody);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", requestdevice)
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp/verify")
         .then()
             .statusCode(422)
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
     assertEquals(code,"GNR_INVALID_DATA");
     assertEquals(description,"Invalid email.");
 }
 @Test
 public void VerifySignupwithInvalidTimestamp() throws Exception {
	 ObjectMapper objectMapper = new ObjectMapper();
 	 
     String requestTimestamp = "25:88:53";
     
     String plain_otp = "443062";
     String requestdevice = "3efe6bbeb55f4411";
     
     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
    
     //encryption with AES256 algorithm0
     //String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
     jsonBody.put("otp", plain_otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin", pin);
     jsonBody.put("pushToken",pushToken);
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     
     //placing token from signup API
     jsonBody.put("token", token);
     

  // Generate signature
     String data = objectMapper.writeValueAsString(jsonBody);
     String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

  // Add signature
     jsonBody.put("signature", requestSignature);
     
     System.out.println(jsonBody);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", requestdevice)
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp/verify")
         .then()
             .statusCode(422)
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
     assertEquals(code,"GNR_INVALID_DATA");
     assertEquals(description,"Invalid Request timestamp.");
	 
 }
// 
@Test
public void VerifySignUpwithInvalidOtp() throws Exception {
	ObjectMapper objectMapper = new ObjectMapper();
	
    
    
    String plain_otp = "5$543";
    String requestdevice = "3efe6bbeb55f4411";
    
    Map<String, Object> jsonBody = new HashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
   
    //encryption with AES256 algorithm0
    String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
    jsonBody.put("otp", otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin", pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel", deviceModel);
    jsonBody.put("deviceColorDepth", deviceColorDepth);
    jsonBody.put("deviceScreenHeight", deviceScreenHeight);
    jsonBody.put("deviceScreenWidth", deviceScreenWidth);
    
    //placing token from signup API
    jsonBody.put("token", token);
    

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

 // Add signature
    jsonBody.put("signature", requestSignature);

    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", requestdevice)
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
@Test
public void VerifySignUpwithInvalidPin() throws Exception {
	ObjectMapper objectMapper = new ObjectMapper();
	
    
    String plain_pin = "89*7";
    String plain_otp = "443062";
    String requestdevice = "3efe6bbeb55f4411";
    
    Map<String, Object> jsonBody = new HashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
   
    //encryption with AES256 algorithm0
    String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
    jsonBody.put("otp", otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin", pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel", deviceModel);
    jsonBody.put("deviceColorDepth", deviceColorDepth);
    jsonBody.put("deviceScreenHeight", deviceScreenHeight);
    jsonBody.put("deviceScreenWidth", deviceScreenWidth);
    
    //placing token from signup API
    jsonBody.put("token", token);
    

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

 // Add signature
    jsonBody.put("signature", requestSignature);

    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", requestdevice)
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
 public void VerifySignUpwithInvalidToken() throws Exception {
	 ObjectMapper objectMapper = new ObjectMapper();
 	
     
     
     String plain_otp = "443062";
     String requestdevice = "3efe6bbeb55f4411";
     
     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
    
     //encryption with AES256 algorithm0
     String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
     jsonBody.put("otp", otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin", pin);
     jsonBody.put("pushToken",pushToken);
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     
     //placing token from signup API
     jsonBody.put("token", "lo$5==");
     

  // Generate signature
     String data = objectMapper.writeValueAsString(jsonBody);
     String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

  // Add signature
     jsonBody.put("signature", requestSignature);

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", requestdevice)
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
 @Test
 public void VerifySignUpwithWrongSignature() throws Exception {
	 //ObjectMapper objectMapper = new ObjectMapper();
 	
     
     
     String plain_otp = "443062";
     String requestdevice = "3efe6bbeb55f4411";
     
     Map<String, Object> jsonBody = new HashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
    
     //encryption with AES256 algorithm0
     String otp = signatureCreate.encryptAES256(plain_otp, secretKey);
     jsonBody.put("otp", otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin", pin);
     jsonBody.put("pushToken",pushToken);
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     
     //placing token from signup API
     jsonBody.put("token", token);
     

  // Generate signature
     //String data = objectMapper.writeValueAsString(jsonBody);
     //String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);

  // Add signature
     jsonBody.put("signature", "3edrHytu&6544-===");

     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id", requestdevice)
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
 public void VerifyOtpwithValidCredentials() throws Exception {
	 ObjectMapper objectMapper = new ObjectMapper();
     Map<String, Object> jsonBody = new LinkedHashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", plain_otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin",pin);
     jsonBody.put("pushToken",pushToken);
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     //placing token from signup API
     jsonBody.put("token", token);
    // System.out.println(jsonBody);

  // Generate signature
     String data = objectMapper.writeValueAsString(jsonBody);
     String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
     
  // Add signature
     jsonBody.put("signature", requestSignature);
     System.out.println("the json"+jsonBody); 
  
     Response response = given()
             .baseUri(baseURI)
             .header("X-GEO-Location", "12,12")
             .header("X-Device-Id",requestdevice)
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/signup/otp/verify")
         .then()
             .statusCode(201)
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
     
     
     
//    // assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
    System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
     
 }
 
 @Test
 public void VerifyOtpwithEmptyPushToken() throws Exception{
	 ObjectMapper objectMapper = new ObjectMapper();
     Map<String, Object> jsonBody = new LinkedHashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", plain_otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin",pin);
     jsonBody.put("pushToken","");
     jsonBody.put("deviceModel", deviceModel);
     jsonBody.put("deviceColorDepth", deviceColorDepth);
     jsonBody.put("deviceScreenHeight", deviceScreenHeight);
     jsonBody.put("deviceScreenWidth", deviceScreenWidth);
     
     //placing token from signup API
     jsonBody.put("token", token);
    // System.out.println(jsonBody);

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
             .post("/signup/otp/verify")
         .then()
             .statusCode(400)
             .log().all()
             .extract().response();
     response.prettyPrint();
     

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
     assertEquals(description,"Push Token cannot be blank.");
     
 }
 
// @Test
// public void VerifyOtpwithexpiredOtp() throws Exception {
//	 ObjectMapper objectMapper = new ObjectMapper();
//	 //ObjectNode jsonBody = JsonNodeFactory.instance.objectNode();
//	// Optional: Don't sort keys alphabetically unless backend expects it
//	 //objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
// 	 
//     
//     
//     
//     
//     
//     Map<String, Object> jsonBody = new LinkedHashMap<>();
//     jsonBody.put("email", email);
//     jsonBody.put("requestTimestamp", requestTimestamp);
//     jsonBody.put("otp", plain_otp);
//     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
//     jsonBody.put("pin",pin);
//     
//     //placing token from signup API
//     jsonBody.put("token", token);
//    // System.out.println(jsonBody);
//
//  // Generate signature
//     String data = objectMapper.writeValueAsString(jsonBody);
//     String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
//     
//  // Add signature
//     jsonBody.put("signature", requestSignature);
//     
//
//     
//     //System.out.println(signatureCreate.decryptAES256(otp,secretKey));
//     //System.out.println(signatureCreate.decryptAES256(pin,secretKey));
//    
//     Response response = given()
//             .baseUri(baseURI)
//             .header("X-GEO-Location", "12,12")
//             .header("X-Device-Id",requestdevice)
//             .header("User-Agent", "NepalTravelApp/1.0.0 android")
//             .contentType("application/json")
//             .body(jsonBody)
//         .when()
//             .post("/signup/otp/verify")
//         .then()
//             .statusCode(200)
//             .log().all()
//             .extract().response();
//     response.prettyPrint();
//     
//
//     // Extracting and asserting response values
//     String code = response.jsonPath().getString("code");
//     String status = response.jsonPath().getString("status");
//     String description = response.jsonPath().getString("description");
//     String signature = response.jsonPath().getString("signature");
//
//     assertNotNull(signature, "Signature is missing");
//     assertNotNull(code, "code is missing from the response");
//     assertNotNull(status, "status is missing from the response");
//     assertNotNull(description, "description in response is missing");
//
//     assertFalse(signature.isEmpty(), "Signature is empty");
//     assertFalse(code.isEmpty(), "code is empty");
//     assertFalse(status.isEmpty(), "status is empty");
//     assertFalse(description.isEmpty(), "description is empty");
//     
//     //assert device and email is equal with request
//     assertEquals(code,"VST_SIGNUP_OTP_FAILED");
//     assertEquals(status,"FAIL");
//     
//    // assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
//     //ystem.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
// }
// 
 
@Test
public void VerifyOtpwithMissingdeviceModel() throws Exception{
	ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> jsonBody = new LinkedHashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
    jsonBody.put("otp", plain_otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin",pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel", "");
    jsonBody.put("deviceColorDepth", deviceColorDepth);
    jsonBody.put("deviceScreenHeight", deviceScreenHeight);
    jsonBody.put("deviceScreenWidth", deviceScreenWidth);
    //placing token from signup API
    jsonBody.put("token", token);
   // System.out.println(jsonBody);

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
    
 // Add signature
    jsonBody.put("signature", requestSignature);
 System.out.println(jsonBody);  
    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id",requestdevice)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("/signup/otp/verify")
        .then()
            .statusCode(400)
            .log().all()
            .extract().response();
    response.prettyPrint();
    
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
    assertEquals(description,"Device Model cannot be blank.");
    
}

@Test
public void VerifyOtpwithEmptyDeviceDetails() throws Exception {
	ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> jsonBody = new LinkedHashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
    jsonBody.put("otp", plain_otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin",pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel", deviceModel);
    jsonBody.put("deviceColorDepth", "");
    jsonBody.put("deviceScreenHeight", "");
    jsonBody.put("deviceScreenWidth", "");
    //placing token from signup API
    jsonBody.put("token", token);
   // System.out.println(jsonBody);

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
    
 // Add signature
    jsonBody.put("signature", requestSignature);
 System.out.println(jsonBody);  
    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id",requestdevice)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("/signup/otp/verify")
        .then()
            .statusCode(422)
            .log().all()
            .extract().response();
    response.prettyPrint();
    
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
    assertEquals(code,"GNR_INVALID_DATA");
    assertEquals(description,"Push Token cannot be blank.");
	
}

@Test
public void VerifyOtpwithInvalidDeviceModel() throws Exception {
	ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> jsonBody = new LinkedHashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
    jsonBody.put("otp", plain_otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin",pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel", 6);
    jsonBody.put("deviceColorDepth", deviceColorDepth);
    jsonBody.put("deviceScreenHeight", deviceScreenHeight);
    jsonBody.put("deviceScreenWidth", deviceScreenWidth);
    //placing token from signup API
    jsonBody.put("token", token);
   // System.out.println(jsonBody);

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
    
 // Add signature
    jsonBody.put("signature", requestSignature);
 System.out.println(jsonBody);  
    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id",requestdevice)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("/signup/otp/verify")
        .then()
            .statusCode(422)
            .log().all()
            .extract().response();
    response.prettyPrint();
    
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
    assertEquals(code,"GNR_INVALID_DATA");
    assertEquals(description,"Invalid Device Model.");
	
}

@Test
public void VerifyOtpwithInvalidColorDepth() throws Exception {
	ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> jsonBody = new LinkedHashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
    jsonBody.put("otp", plain_otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin",pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel",deviceModel);
    jsonBody.put("deviceColorDepth", -1);
    jsonBody.put("deviceScreenHeight", deviceScreenHeight);
    jsonBody.put("deviceScreenWidth", deviceScreenWidth);
    //placing token from signup API
    jsonBody.put("token", token);
   // System.out.println(jsonBody);

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
    
 // Add signature
    jsonBody.put("signature", requestSignature);
 System.out.println(jsonBody);  
    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id",requestdevice)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("/signup/otp/verify")
        .then()
            .statusCode(422)
            .log().all()
            .extract().response();
    response.prettyPrint();
    
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
    assertEquals(code,"GNR_INVALID_DATA");
    assertEquals(description,"Invalid color depth.");
	
}
@Test
public void VerifyOtpwithInvalidScreenHeight() throws Exception {
	ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> jsonBody = new LinkedHashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
    jsonBody.put("otp", plain_otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin",pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel", deviceModel);
    jsonBody.put("deviceColorDepth", deviceColorDepth);
    jsonBody.put("deviceScreenHeight", 5);
    jsonBody.put("deviceScreenWidth", deviceScreenWidth);
    //placing token from signup API
    jsonBody.put("token", token);
   // System.out.println(jsonBody);

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
    
 // Add signature
    jsonBody.put("signature", requestSignature);
 System.out.println(jsonBody);  
    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id",requestdevice)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("/signup/otp/verify")
        .then()
            .statusCode(422)
            .log().all()
            .extract().response();
    response.prettyPrint();
    
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
    assertEquals(code,"GNR_INVALID_DATA");
    assertEquals(description,"Invalid screen height.");
	
}

@Test
public void VerifyOtpwithInvalidScreenWidth() throws Exception {
	ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> jsonBody = new LinkedHashMap<>();
    jsonBody.put("email", email);
    jsonBody.put("requestTimestamp", requestTimestamp);
    jsonBody.put("otp", plain_otp);
    String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
    jsonBody.put("pin",pin);
    jsonBody.put("pushToken",pushToken);
    jsonBody.put("deviceModel", deviceModel);
    jsonBody.put("deviceColorDepth", deviceColorDepth);
    jsonBody.put("deviceScreenHeight", deviceScreenHeight);
    jsonBody.put("deviceScreenWidth", 7);
    //placing token from signup API
    jsonBody.put("token", token);
   // System.out.println(jsonBody);

 // Generate signature
    String data = objectMapper.writeValueAsString(jsonBody);
    String requestSignature = signatureCreate.generateHMACSHA256(data,secretKey);
    
 // Add signature
    jsonBody.put("signature", requestSignature);
 System.out.println(jsonBody);  
    Response response = given()
            .baseUri(baseURI)
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id",requestdevice)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .contentType("application/json")
            .body(jsonBody)
        .when()
            .post("/signup/otp/verify")
        .then()
            .statusCode(422)
            .log().all()
            .extract().response();
    response.prettyPrint();
    
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
    assertEquals(code,"GNR_INVALID_DATA");
    assertEquals(description,"Invalid screen width.");
	
}
}
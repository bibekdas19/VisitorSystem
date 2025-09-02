package OTP;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

public class verifyresetOtp {
	String baseURI = "https://visitor0.moco.com.np/visitor";
    String secretKey = "f7b0ae107d4f52b3a219eb34fd2e126bfa06cf26b7f4805bf5e7447d32c19584";
    String token = "OSWFnVf7UKtEh5SG7WuyrphWiwW8CwBDDs8GQgKE8uM";
    String email = "samwekshakya65@gmail.com";
    String requestTimestamp = signatureCreate.generateTimestamp();
    String plain_pin = "123654";
    String plain_otp = "500376";
    String requestdevice = "samwek-device";

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
                .header("X-Device-Id", "")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/reset/otp/verify")
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
                .post("/reset/otp/verify")
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
                .post("/reset/otp/verify")
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
                .post("/reset/otp/verify")
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
            .post("/reset/otp/verify")
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
             .post("/reset/otp/verify")
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
             .post("/reset/otp/verify")
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
             .post("/reset/otp/verify")
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
     
     //placing token from signup API
     jsonBody.put("token", token);
     

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
             .post("/reset/otp/verify")
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
             .post("/reset/otp/verify")
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
     assertEquals(description,"Invalid Data.");
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
             .post("/reset/otp/verify")
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
     assertEquals(description,"Invalid Data.");
	 
 }
 
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
            .post("/reset/otp/verify")
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
            .post("/reset/otp/verify")
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
             .post("/reset/otp/verify")
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
             .post("/reset/otp/verify")
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
 public void VerifyOtpwithInactiveDevice() throws Exception {
	 ObjectMapper objectMapper = new ObjectMapper();
     Map<String, Object> jsonBody = new LinkedHashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", plain_otp);
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin",pin);
     
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
             .header("X-Device-Id","visitor_app_device2")
             .header("User-Agent", "NepalTravelApp/1.0.0 android")
             .contentType("application/json")
             .body(jsonBody)
         .when()
             .post("/reset/otp/verify")
         .then()
             .statusCode(401)
             .log().all()
             .extract().response();
     response.prettyPrint();
     

     // Extracting and asserting response values
//     String code = response.jsonPath().getString("code");
//     String status = response.jsonPath().getString("status");
//     String description = response.jsonPath().getString("description");
//     
//
//     
//     assertNotNull(status, "sessionkey is missing from the response");
//     assertNotNull(description, "description is missing from the response");
//     assertNotNull(code, "code in reponse is missing");
//
//    
//     assertFalse(description.isEmpty(), "Device ID is empty");
//     assertFalse(code.isEmpty(), "code is empty");
//     assertFalse(status.isEmpty(), "status is empty");
//     
//     //assert device and email is equal with request
//     assertEquals(code,"GNR_OK");
//     assertEquals(status,"SUCCESS");
//     
//    // assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
//     System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
//     
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
             .post("/reset/otp/verify")
         .then()
             .statusCode(200)
             .log().all()
             .extract().response();
     response.prettyPrint();
     

     // Extracting and asserting response values
     String code = response.jsonPath().getString("code");
     String status = response.jsonPath().getString("status");
     String description = response.jsonPath().getString("description");
     String signature = response.jsonPath().getString("signature");

     assertNotNull(signature, "Signature is missing");
     assertNotNull(status, "sessionkey is missing from the response");
     assertNotNull(description, "description is missing from the response");
     assertNotNull(code, "code in reponse is missing");

     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(description.isEmpty(), "Device ID is empty");
     assertFalse(code.isEmpty(), "code is empty");
     assertFalse(status.isEmpty(), "status is empty");
     
     //assert device and email is equal with request
     assertEquals(code,"GNR_OK");
     assertEquals(status,"SUCCESS");
     
   //verify signature
     //matching response signature with calculated hash
     Map<String, Object> fields = new LinkedHashMap<>();
     fields.put("code", code);
     fields.put("status", status);
     fields.put("description", description);
     

     String partialJson = objectMapper.writeValueAsString(fields);
     String partialSignature = signatureCreate.generateHMACSHA256(partialJson, secretKey);
     assertEquals(signature, partialSignature);
     
    // assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
     System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
     
 }
 
 @Test
 public void VerifyOtpwithexpiredOtp() throws Exception {
	 ObjectMapper objectMapper = new ObjectMapper();
     Map<String, Object> jsonBody = new LinkedHashMap<>();
     jsonBody.put("email", email);
     jsonBody.put("requestTimestamp", requestTimestamp);
     jsonBody.put("otp", "252586");
     String pin = signatureCreate.encryptAES256(plain_pin, secretKey);
     jsonBody.put("pin",pin);
     
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
             .post("/reset/otp/verify")
         .then()
             .statusCode(200)
             .log().all()
             .extract().response();
     response.prettyPrint();
     

     // Extracting and asserting response values
     String code = response.jsonPath().getString("code");
     String status = response.jsonPath().getString("status");
     String description = response.jsonPath().getString("description");
     String signature = response.jsonPath().getString("signature");

     assertNotNull(signature, "Signature is missing");
     assertNotNull(status, "sessionkey is missing from the response");
     assertNotNull(description, "description is missing from the response");
     assertNotNull(code, "code in reponse is missing");

     assertFalse(signature.isEmpty(), "Signature is empty");
     assertFalse(description.isEmpty(), "Device ID is empty");
     assertFalse(code.isEmpty(), "code is empty");
     assertFalse(status.isEmpty(), "status is empty");
     
     //assert device and email is equal with request
     assertEquals(code,"VST_RESET_OTP_FAILED");
     assertEquals(status,"USED");
     assertEquals(description,"Reset PIN failed due to invalid OTP.");
     
    // assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
     System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
     
 }

 


}

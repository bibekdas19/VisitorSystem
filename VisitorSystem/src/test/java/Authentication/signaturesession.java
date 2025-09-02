package Authentication;

import static io.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.Map;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class signaturesession {
	String requestDeviceId = "visitor-app-device";
	String secretKey = "ABC123XYZ";
	String AuthToken;
	String input_email = "vivek@moco.com.np";
	String input_pin = "123654";
	@BeforeClass
	public void getToken() throws Exception{
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response1 = given()
                .header("X-GEO-LOCATION", "12,12")
                .header("X-DEVICE-ID", requestDeviceId)
                .header("User-Agent", "TravelApp/1.0.0 android")
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
                .header("X-GEO-LOCATION", "12,12")
                .header("X-DEVICE-ID", requestDeviceId)
                .header("User-Agent", "TravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
       AuthToken = response2.getHeader("X-AUTH-TOKEN");
	    //String secretKey2 = response2.jsonPath().getString("sessionKey");
	}
	
	@Test
	public void getSignature() throws Exception {
	  ObjectMapper objectMapper = new ObjectMapper();
      
      Map<String, Object> headers = new LinkedHashMap<>();
      headers.put("X-GEO-LOCATION", "12,12");
      headers.put("X-DEVICE-ID", requestDeviceId);
      headers.put("User-Agent", "TravelApp/1.0.0 android");
      headers.put("X-AUTH-TOKEN", AuthToken);
      headers.put("X-REQUEST-TIMESTAMP", "2025-05-13 14:38:00");
      
      headers.put("X-SYSTEM-ID", "discover");
      //headers.put("X-CREDENTIAL", "");
      
      

      Map<String, Object> jsonBody = new LinkedHashMap<>();
      jsonBody.put("headers",headers);
      
   // Generate signature
      String data = objectMapper.writeValueAsString(jsonBody);
      
      
      String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
      
      headers.put("X-SYSTEM-SIGNATURE", requestSignature);
      
      System.out.println(jsonBody);
	}

}

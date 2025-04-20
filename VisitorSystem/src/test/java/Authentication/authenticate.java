package Authentication;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;


public class authenticate {
     String baseURI = "https://visitor0.moco.com.np/visitor";
    String secretKey;

    @BeforeClass
    public void getSecretKey() {
        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .when()
                .get("/key")
            .then()
                .statusCode(200)
                .extract().response();

        secretKey = response.jsonPath().getString("signOnKey");
        assertNotNull(secretKey, "Secret key is null!");
    }


	@Test
	public void GetToken() {
		String requestDeviceId = "3efe6bbeb55f4411";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", signatureCreate.generateHMACSHA256());
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(200);
        //check if the device id is same in request and response
        assertEquals(requestDeviceId,response.jsonPath().getString("deviceId"));
	}
	
	@Test
	public void AuthenticateWithNoLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
        
	}
	
	@Test
	public void AuthenticateWithNoDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
		
	}
	
	@Test
	public void AuthenticateWithoutUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithInvalidLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	@Test
	public void AuthenticateWithInvalidDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "pp")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	@Test
	public void AuthenticateWithInvalidUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	@Test
	public void AuthenticateWithMissingEmail() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        //credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
		
	}
	@Test
	public void AuthenticateWithMissingPin() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        //credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
		
	}
	
	@Test
	public void AuthenticateWithEmptyEmail() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
		
	}
	
	@Test
	public void AuthenticateWithInvalidEmail() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.p.com");
        //credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithEmptyPin() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithInvalidPin() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234AAA");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(422);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithMissingSignature() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        //credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        //requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithEmptySignature() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        //credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithNullValue() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "NULL");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(401);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
	@Test
	public void AuthenticateWithExceedValues() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234566666677");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12aa12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 andrid....")
            .body(requestBody)
        .when()
            .post("/authenticate")
        .then()
            .statusCode(400);
        String signature = response.jsonPath().getString("signature");
        String signOnKey = response.jsonPath().getString("signOnKey");
        String deviceId = response.jsonPath().getString("deviceId");
        // Check if the signature is not null or empty
       
        assertNotNull(signature, "signature is missing");
        assertNotNull(signOnKey, "signon key is missing from the response");
        assertNotNull(deviceId, "device id is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(signOnKey.isEmpty(),"sign on key is empty in the response");
        assertFalse(deviceId.isEmpty(),"device id is empty in the response");
	}
	
}

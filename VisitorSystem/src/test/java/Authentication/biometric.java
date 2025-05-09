package Authentication;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;
public class biometric {
	@Test
	public void biometricWithValid() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
	}
	@Test
	public void setBiometericwithoutDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
		
	}
	@Test
	public void setBiometricwithoutLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
	}
    @Test
    public void setBiometricwithoutUserAgent() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithoutAuth() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithInvalidDevice() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithInvalidUserAgent() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithInvalidLocation() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithInvalidAuth() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithoutrequest() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithoutHash() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithoutSignature() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithInvalidHash() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
    @Test
    public void setBiometricwithInvalidSignature() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("hash", "string");
		jsonMap.put("signature", "string");
		
		Response response = (Response) given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "3efe6bbeb55f4411")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .body(jsonMap)
	        .when()
	            .post("/biometric")
	        .then()
	            .statusCode(200);
		
		String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        assertNotNull(signature, "description is missing from the response");
        assertFalse(signature.isEmpty(), "description is empty in the response");
    }
}

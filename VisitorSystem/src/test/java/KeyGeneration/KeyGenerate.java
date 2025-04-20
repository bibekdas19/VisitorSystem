package KeyGeneration;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class KeyGenerate {
	@Test
	public void GetKey() {
        baseURI = "https://visitor0.moco.com.np/visitor";
        String requestDeviceId = "3efe6bbeb55f4411";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .get("/key")
        .then()
            .statusCode(200)
            .extract().response();
        
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
        
		// check if the request and response have same value for device id
        assertEquals(requestDeviceId,deviceId);
	}
	
	@Test
	public void NoDeviceId() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
	}
	
	@Test
	public void NoGeoLocation() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
	}
	
	@Test
	public void NoUserAgent() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
	}
	@Test
	public void MissingGeo() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
           // .header("X-GEO-Location", "")
        		.header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
	}
	@Test
	public void MissingDevice() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "")
            //.header("X-Device-Id", "")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
	}
	@Test
	public void MissingUserAgent() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            //.header("User-Agent", "asase12")
            .header("Accept", "") 
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
	}
	
	@Test
	public void InvalidDeviceId() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "abc-@d")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .get("/key")
        .then()
            .statusCode(422)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid device Id found.");
	}
	
	@Test
	public void InvalidGeo() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "loo")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .get("/key")
        .then()
            .statusCode(422)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid Geo location found.");
	}

	@Test
	public void InvalidUserAgent() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(422)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid user agent found.");
	}
	
	@Test
	public void SystemError() {
       baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .get("/key")
        .then()
            .statusCode(500)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_ERR");
         assertEquals(description,"Internal Server Error");
	}
	}


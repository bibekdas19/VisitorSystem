package KeyGeneration;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class KeyGenerate {
	@Test
	public void GetKey() {
        baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "abchde")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(200)
            .extract().response();
        String signature = response.jsonPath().getString("signature");
        // Check if the signature is not null or empty
        assertNotNull(signature, "Signature is missing from the response");
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
		
	}
	
	@Test
	public void NoDeviceId() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	
	@Test
	public void NoGeoLocation() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	
	@Test
	public void NoUserAgent() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "")
            .header("User-Agent", "")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	@Test
	public void NoMissingGeo() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
           // .header("X-GEO-Location", "")
            .header("X-Device-Id", "")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	@Test
	public void NoMissingDevice() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "")
            //.header("X-Device-Id", "")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	@Test
	public void NoMissingUserAgent() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "")
            //.header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	
	@Test
	public void InvalidDeviceId() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(422)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	
	@Test
	public void InvalidGeo() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(422)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	@Test
	public void InvalidUserAgent() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(422)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}
	
	@Test
	public void SystemError() {
       baseURI = "https://test0.moco.com.np";
        
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "")
            .header("User-Agent", "asase12")
        .when()
            .get("/key")
        .then()
            .statusCode(500)
            .extract().response();
        String code = response.jsonPath().getString("code");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
	}

}

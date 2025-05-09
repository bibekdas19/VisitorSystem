package Authentication;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class logOut {
	@Test
	public void LogoutwithValidCredentials() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	
	@Test
	public void logoutwithoutDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	@Test
	public void logoutwithoutAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	@Test
	public void logoutwithoutLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	@Test
	public void logoutwithoutUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	@Test
	public void logoutwithInvalidDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "@##")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	
	@Test
	public void logoutwithInvalidLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12AA12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	@Test
	public void logoutwithInvalidUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTrave1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	@Test
	public void logoutwithInvalidAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
	@Test
	public void logoutwithoutLogin() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}

}

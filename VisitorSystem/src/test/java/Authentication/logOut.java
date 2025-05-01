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
		
	}
	@Test
	public void logoutwithoutAuth() {
		
	}
	@Test
	public void logoutwithoutLocation() {
		
	}
	@Test
	public void logoutwithoutUserAgent() {
		
	}
	@Test
	public void logoutwithInvalidDevice() {
		
	}
	
	@Test
	public void logoutwithInvalidLocation() {
		
	}
	@Test
	public void logoutwithInvalidUserAgent() {
		
	}
	@Test
	public void logoutwithInvalidAuth() {
		
	}
	@Test
	public void logoutwithoutLogin() {
		
	}

}

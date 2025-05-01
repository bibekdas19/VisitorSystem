package Authentication;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class deleteBiometric {
	@Test
	public void deleteBiometricWithValidCredentials() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-Location", "12,12")
            .header("X-AUTH-TOKEN","sdsd")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
        .when()
            .delete("/biometric")
        .then()
            .statusCode(200)
            .extract().response();
        
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
	public void deleteBiometricwithoutDevice() {
		
	}
	@Test
	public void deleteBiometricwithoutAuth() {
		
	}
	@Test
	public void deleteBiometricwithoutLocation() {
		
	}
	@Test
	public void deleteBiometricwithoutUserAgent() {
		
	}
	@Test
	public void deleteBiometricwithInvalidDevice() {
		
	}
	
	@Test
	public void deleteBiometricwithInvalidLocation() {
		
	}
	@Test
	public void deleteBiometricwithInvalidUserAgent() {
		
	}
	@Test
	public void deleteBiometricwithInvalidAuth() {
		
	}
	@Test
	public void deleteBiometricwithoutsetting() {
		
	}
	


}

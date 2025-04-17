package Authentication;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class session {
	@Test
	public void GetSessionInformation() {
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

}

package Authentication;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;


public class authenticate {
	@Test
	public void GetToken() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		String requestDeviceId = "3efe6bbeb55f4411";
		Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("pin", "1234");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("credentials", credentials);
        requestBody.put("signature", "your-signature-string");
        
        Response response = (Response) given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "3efe6bbeb55f4411")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .body(requestBody)
        .when()
            .get("/authenticate")
        .then()
            .statusCode(200);
        //check if the device id is same in request and response
        assertEquals(requestDeviceId,response.jsonPath().getString("deviceId"));
	}

}

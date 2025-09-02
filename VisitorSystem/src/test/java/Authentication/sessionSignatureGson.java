package Authentication;

import static io.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class sessionSignatureGson {
	String requestDeviceId = "793d1ae9c317736b";
	String secretKey = "ABC123XYZ";
	String AuthToken;
	String input_email = "sharad@moco.com.np";
	String input_pin = "147258";
	String SessionSecretKey;

    @BeforeClass
    public void getToken() throws Exception {
        RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

        // Step 1: Get signOnKey
        Response response1 = given()
                .header("X-GEO-LOCATION", "12,12")
                .header("X-DEVICE-ID", requestDeviceId)
                .header("User-Agent", "TravelApp/1.0.0 android")
            .when()
                .get("/key")
            .then()
                .statusCode(200)
                .extract().response();

        String signOnKey = response1.jsonPath().getString("signOnKey");

        // Step 2: Encrypt PIN and prepare payload
        String encryptedPin = signatureCreate.encryptAES256(input_pin, signOnKey);

        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", input_email);
        credentials.put("pin", encryptedPin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);

        // Step 3: Serialize and generate signature using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(jsonBody);
        String signature = signatureCreate.generateHMACSHA256(jsonData, signOnKey);

        jsonBody.put("signature", signature);

        // Step 4: Authenticate request
        Response response2 = given()
                .header("X-GEO-LOCATION", "12,12")
                .header("X-DEVICE-ID", requestDeviceId)
                .header("User-Agent", "TravelApp/1.0.0 android")
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(jsonBody)) // use Jackson here too
            .when()
                .post("/authenticate")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        AuthToken = response2.getHeader("X-AUTH-TOKEN");
        SessionSecretKey = response2.jsonPath().getString("sessionKey");
    }

    @Test
    public void getSignature() throws Exception {
        Gson gson = new Gson();

        // Prepare headers
        Map<String, Object> headers = new LinkedHashMap<>();
        headers.put("X-GEO-LOCATION", "12,12");
        headers.put("X-AUTH-TOKEN", AuthToken);
        headers.put("X-DEVICE-ID", requestDeviceId);
        headers.put("User-Agent", "TravelApp/1.0.0 android");
       
        headers.put("X-REQUEST-TIMESTAMP", "2025-05-13 14:38:00");
        headers.put("X-SYSTEM-ID", "discover");

        // Prepare request JSON
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("headers", headers);

        String jsonData = gson.toJson(jsonBody);

        // Sign using the static key
        String requestSignature = signatureCreate.generateHMACSHA256(jsonData, SessionSecretKey);
        headers.put("X-SYSTEM-SIGNATURE", requestSignature);

        // Output final JSON
        System.out.println(gson.toJson(jsonBody));
    }
}

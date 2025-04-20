package OTP;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.*;

public class signupOtp {

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
    public void signUpwithValidCredentials() throws Exception {
        String email = "vivek@moco.com.np";
        String requestTimestamp = signatureCreate.generateTimestamp();
        String data = signatureCreate.jsonStringify(jsonBody);

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String token = response.jsonPath().getString("deviceId");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(token, "Device ID is missing from the response");
        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(token.isEmpty(), "Device ID is empty");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
    }
    
    @Test
    public void signUpwithoutDevice() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    	
    }
    
    @Test
    public void signUpwithoutUserAgent() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    	
    }
    
    @Test
    public void signUpWithEmptyLocation() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "")
                .header("X-Device-Id", "3efe6bbeb55f4411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    	
    	
    }
    @Test
    public void signUpwithInvalidDevice() throws Exception {
        	String email = "vivek@moco.com.np";
            String requestTimestamp = "2025-04-20 10:22:00";
            String data = email + requestTimestamp;

            String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

            Map<String, Object> jsonBody = new HashMap<>();
            jsonBody.put("email", email);
            jsonBody.put("requestTimestamp", requestTimestamp);
            jsonBody.put("signature", requestSignature);

            Response response = given()
                    .baseUri(baseURI)
                    .header("X-GEO-Location", "12,12")
                    .header("X-Device-Id", "3efe6bbeb55f@@411")
                    .header("User-Agent", "NepalTravelApp/1.0.0 android")
                    .contentType("application/json")
                    .body(jsonBody)
                .when()
                    .post("/signup/otp")
                .then()
                    .statusCode(422)
                    .log().all()
                    .extract().response();

            // Extracting and asserting response values
            String code = response.jsonPath().getString("code");
            String description = response.jsonPath().getString("description");

            assertNotNull(description, "Description is missing from the response");
            assertNotNull(code, "Code is missing");
            assertFalse(description.isEmpty(), "Description is empty");
            assertFalse(code.isEmpty(), "Code is empty");
            assertEquals(code,"GNR_INVALID_DATA");
            assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void signUpwithInvalidLocation() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12aa")
                .header("X-Device-Id", "3efe6bbeb55f411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Bad Request.");
    	
    }
    @Test
    public void signUpwithInvalidUserAgent() throws Exception {
        	String email = "vivek@moco.com.np";
            String requestTimestamp = "2025-04-20 10:22:00";
            String data = email + requestTimestamp;

            String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

            Map<String, Object> jsonBody = new HashMap<>();
            jsonBody.put("email", email);
            jsonBody.put("requestTimestamp", requestTimestamp);
            jsonBody.put("signature", requestSignature);

            Response response = given()
                    .baseUri(baseURI)
                    .header("X-GEO-Location", "12,12aa")
                    .header("X-Device-Id", "3efe6bbeb55f411")
                    .header("User-Agent", "NepalTravelApp/1.0.0 androidqqqq")
                    .contentType("application/json")
                    .body(jsonBody)
                .when()
                    .post("/signup/otp")
                .then()
                    .statusCode(422)
                    .log().all()
                    .extract().response();

            // Extracting and asserting response values
            String code = response.jsonPath().getString("code");
            String description = response.jsonPath().getString("description");

            assertNotNull(description, "Description is missing from the response");
            assertNotNull(code, "Code is missing");
            assertFalse(description.isEmpty(), "Description is empty");
            assertFalse(code.isEmpty(), "Code is empty");
            assertEquals(code,"GNR_INVALID_DATA");
            assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void signUpwithEmptyEmail() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", "");
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    	
    }
    
    @Test
    public void signUpwithEmptyTimestamp() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", "");
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    	
    }
    
    @Test
    public void signUpwithNoSignature() throws Exception {
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        //String data = email + requestTimestamp;

        //String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", "");

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void signUpwithInvalidTime() throws Exception{
    	String email = "vivek@moco.com.np";
        String requestTimestamp = "2025-15-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void signUpWithInvalidEmailformat() throws Exception{
    	String email = "vivek@moco@Scom.np";
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void signUpwithDuplicateDevice() throws Exception{
    	String email = "vivek@moco.com.np"; 
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f411") //already signed up device
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(409)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"VST_SIGNUP_DEVICE");
        assertEquals(description,"Device already exists.");
    	
    }
    
    @Test
    public void signUpwithDuplicateEmail() throws Exception {
    	String email = "vivek@moco.com.np"; //already signed up email
        String requestTimestamp = "2025-04-20 10:22:00";
        String data = email + requestTimestamp;

        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);
        jsonBody.put("signature", requestSignature);

        Response response = given()
                .baseUri(baseURI)
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", "3efe6bbeb55f411")
                .header("User-Agent", "NepalTravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/signup/otp")
            .then()
                .statusCode(409)
                .log().all()
                .extract().response();

        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"VST_SIGNUP_EMAIL");
        assertEquals(description,"Email already exists.");
    	
    }
}

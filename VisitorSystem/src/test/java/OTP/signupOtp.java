package OTP;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class signupOtp {

    String secretKey;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

        Response response = given()
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
        ObjectMapper objectMapper = new ObjectMapper();
        String email = "vivek@moco.com.np";
        String requestTimestamp = signatureCreate.generateTimestamp();

        // Prepare payload without signature
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("requestTimestamp", requestTimestamp);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

        // Add signature
        jsonBody.put("signature", requestSignature);

        // Send request
        Response response = given()
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

        // Assertions
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String token = response.jsonPath().getString("token");
        String responseSignature = response.jsonPath().getString("signature");

        assertNotNull(responseSignature, "Signature is missing");
        assertNotNull(token, "token is missing from the response");
        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");

        assertFalse(responseSignature.isEmpty(), "Signature is empty");
        assertFalse(token.isEmpty(), "token is empty");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
    }


    
    @Test
    public void signUpwithoutDevice() throws Exception {
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare request without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
            assertEquals(description,"Invalid device Id found.");
    }
    
    @Test
    public void signUpwithInvalidLocation() throws Exception {
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
        assertEquals(description,"Invalid Geo location found.");
    	
    }
    @Test
    public void signUpwithInvalidUserAgent() throws Exception {
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
         jsonBody.put("signature", requestSignature);

            Response response = given()
                    .baseUri(baseURI)
                    .header("X-GEO-Location", "12,12")
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
            assertEquals(description,"Invalid user agent found.");
    }
    
    @Test
    public void signUpwithEmptyEmail() throws Exception {
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         //String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", "");

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
    	// ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         //String data = objectMapper.writeValueAsString(jsonBody);
         //String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
        // String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", "25:55:99");

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moc.l..o.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
        assertEquals(description,"Invalid email found.");
    }
    
    @Test
    public void signUpwithDuplicateDevice() throws Exception{
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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
    	 ObjectMapper objectMapper = new ObjectMapper();
         String email = "vivek@moco.com.np";
         String requestTimestamp = signatureCreate.generateTimestamp();

         // Prepare payload without signature
         Map<String, Object> jsonBody = new HashMap<>();
         jsonBody.put("email", email);
         jsonBody.put("requestTimestamp", requestTimestamp);

         // Generate signature
         String data = objectMapper.writeValueAsString(jsonBody);
         String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

         // Add signature
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

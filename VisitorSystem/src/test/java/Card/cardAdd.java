package Card;

import static io.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

public class cardAdd {

    // Test data
    String card = "4957030005123304";
    String requestDeviceId = "c478cfac4148512";
    String AuthToken = "eyJhbGciOiJIUzM4NCJ9..."; // Shortened for clarity
    String secretKey = "6da86ea166633a8130c999522a1a9cec1c89c166f265ff8c309d8f6652342373";

    @Test
    public void getUserDetails() throws Exception {
        // Set base URI
        RestAssured.baseURI = "https://visitor0.moco.com.np/payment";

        ObjectMapper objectMapper = new ObjectMapper();

        // Prepare request body
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        String encryptedCard = signatureCreate.encryptAESCard(card, secretKey);

        jsonBody.put("card", encryptedCard);
        jsonBody.put("expYear", "28");
        jsonBody.put("expMonth", "5");
        jsonBody.put("requestTimestamp", signatureCreate.generateTimestamp());

        // Convert map to JSON string for signature
        String jsonString = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(jsonString, secretKey);

        jsonBody.put("signature", requestSignature);

        // Final payload
        
        System.out.println(jsonBody);

//        // Send the POST request
//        Response response = given()
//                .header("X-GEO-Location", "27.6851596,85.3203065")
//                .header("X-Device-Id", requestDeviceId)
//                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//                .header("X-AUTH-TOKEN", AuthToken)
//                .contentType("application/json")
//                .body(finalPayload)
//                .when()
//                .post("/authorize/card")
//                .then()
//                .statusCode(200) // Update if needed
//                .log().all()
//                .extract().response();
//
//        // Optional: Print response
//        response.prettyPrint();
    }
}

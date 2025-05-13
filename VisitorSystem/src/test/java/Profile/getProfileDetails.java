package Profile;

//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

//import java.util.*;

public class getProfileDetails {
	String AuthToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ2aXZla0Btb2NvLmNvbS5ucCIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tdHJhdmVsLWFwcCIsImlhdCI6MTc0NzExMjAwNSwiZXhwIjoxNzQ3MTQyMDA1fQ.XxlXO-cniFD7sy6A841T84w8rLtJi-vebQjQ7qqkfOmeI7yCdW3iypLQlsTr_3ke";

//	@BeforeClass
//	public void getToken() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		//get the signOn key
//		Response keyResponse = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", "travel-app-phone")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.when()
//				.get("/key")
//				.then()
//				.statusCode(200)
//				.extract().response();
//
//		String secretKey = keyResponse.jsonPath().getString("signOnKey");
//		assertNotNull(secretKey, "Secret key is null!");
//
//		//authenticate before for the auth token
//		ObjectMapper objectMapper = new ObjectMapper();
//		String email = "vivek@moco.com.np";
//		String requestDeviceId = "travel-app-phone";
//		Map<String, Object> credentials = new HashMap<>();
//		credentials.put("email", email);
//		credentials.put("pin", "123426");
//
//		Map<String, Object> jsonBody = new HashMap<>();
//		jsonBody.put("credentials", credentials);
//
//		// Generate signature
//		String data = objectMapper.writeValueAsString(jsonBody);
//		String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
//
//		jsonBody.put("signature", requestSignature);
//
//		// Send request
//		Response Authresponse = given()
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", requestDeviceId)
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.contentType("application/json")
//				.body(jsonBody)
//				.when()
//				.post("authenticate")
//				.then()
//				.statusCode(200)
//				.log().all()
//				.extract().response();
//		AuthToken = Authresponse.getHeader("X-AUTH-TOKEN");
//
//	}
	
	@Test
	public void getProfileInformation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "moco-travel-app")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(200)
	            .extract().response();
		response.prettyPrint();
		
		String signature = response.jsonPath().getString("signature");
		String fullName = response.jsonPath().getString("fullName");
		String country = response.jsonPath().getString("country");
		String documentNumber = response.jsonPath().getString("documentNumber");
		String dateOfBirth = response.jsonPath().getString("dateOfBirth");
		String documentExpiryDate = response.jsonPath().getString("documentExpiryDate");
		String gender = response.jsonPath().getString("gender");
		String documentType = response.jsonPath().getString("documentType");
		String status = response.jsonPath().getString("status");
        String verificationStatus = response.jsonPath().getString("verificationStatus");
        String loginAttemptCountPin = response.jsonPath().getString("loginAttemptCountPin");
        String loginAttemptCountBiometric = response.jsonPath().getString("loginAttemptCountBiometric");
        
		

		assertNotNull(signature, "signature is missing");
		assertNotNull(fullName, "fullname is missing");
		assertNotNull(country, "country is missing");
		assertNotNull(signature,"signature is missing");
		assertNotNull(documentNumber, "documentNumber is missing");
        assertNotNull(documentType, "documentType is missing");
        assertNotNull(dateOfBirth, "dateOfBirth is missing");
        assertNotNull(documentExpiryDate, "documentExpiryDate is missing");
        assertNotNull(gender, "gender is missing");
        assertNotNull(status, "status is missing");
        assertNotNull(verificationStatus, "verificationStatus is missing");
        assertNotNull(loginAttemptCountPin, "loginAttemptCount is missing");
        assertNotNull(loginAttemptCountBiometric, "isBiometric is missing");

        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(fullName.isEmpty(), "fullname is missing");
        assertFalse(country.isEmpty(), "country is missing");
        assertFalse(documentNumber.isEmpty(), "documentNumber is missing");
        assertFalse(documentType.isEmpty(), "documentType is missing");
        assertFalse(dateOfBirth.isEmpty(), "dateOfBirth is missing");
        assertFalse(documentExpiryDate.isEmpty(), "documentExpiryDate is missing");
        assertFalse(gender.isEmpty(), "gender is missing");
        assertFalse(status.isEmpty(), "status is missing");
        assertFalse(verificationStatus.isEmpty(), "verificationStatus is missing");
        assertFalse(loginAttemptCountPin.isEmpty(), "loginAttemptCount is missing");
        assertFalse(loginAttemptCountBiometric.isEmpty(), "isBiometric is missing");
        
        System.out.println(status);
      //check if the token is in the present in the response
        assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"), "Missing X-AUTH-TOKEN header");
        System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
		
	}
	@Test
	public void getProfileInformationwithoutDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(400)
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
	public void getProfileInformationwithoutGeo() {
    	baseURI = "https://visitor0.moco.com.np/visitor";
    	Response response = given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(400)
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
	public void getProfileInformationwithoutUserAgent() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(400)
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
	public void getProfileInformationwithoutAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN","")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(400)
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
	public void getProfileInformationwithInvalidDevice() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone#$")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(422)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	    // String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	    // assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     //assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid device Id found.");
	}
	@Test
	public void getProfileInformationwithInvalidLocation() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12AA12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(422)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     //String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	    // assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     //assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_INVALID_DATA");
		 assertEquals(description,"Invalid Geo location found.");
		
	}
	@Test
	public void getProfileInformationwithInvalidAuth() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN","()(jd")
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(401)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	    // String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     //assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     //assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_AUTHENTICATION_FAIL");
		 assertEquals(description,"Authentication Failed.");
		
	}
	@Test
	public void getProfileInformationwithServerDown() {
		baseURI = "https://visitor0.moco.com.np/visitor";
		Response response = given()
	            .header("X-GEO-Location", "12,12")
	            .header("X-Device-Id", "travel-app-phone")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	        .when()
	            .get("/profile")
	        .then()
	            .statusCode(500)
	            .extract().response();
		
		// Extracting and asserting response values
		String code = response.jsonPath().getString("code");
	     String description = response.jsonPath().getString("description");
	     String signature = response.jsonPath().getString("signature");
	     
	     assertNotNull(description, "Description is missing from the response");
	     assertNotNull(code, "Code is missing");
	     assertNotNull(signature,"signature is missing");
	     
	     assertFalse(description.isEmpty(), "Description is empty");
	     assertFalse(code.isEmpty(), "Code is empty");
	     assertFalse(signature.isEmpty(),"signature is empty");
	     
	     assertEquals(code,"GNR_ERR");
		 assertEquals(description,"Internal Server Error");
		
		
	}
}   


package Authentication;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class session {
	
	String requestDeviceId = "visitor-app-device";
	String secretKey = "ABC123XYZ";
	String AuthToken;
	String input_email = "vivek@moco.com.np";
	String input_pin = "123653";
	@BeforeClass
	public void getToken() throws Exception{
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response1 = given()
                .header("X-GEO-LOCATION", "12,12")
                .header("X-DEVICE-ID", requestDeviceId)
                .header("User-Agent", "TravelApp/1.0.0 android")
            .when()
                .get("/key")
            .then()
                .statusCode(200)
                .extract().response();

       String secretKey1 = response1.jsonPath().getString("signOnKey");
        //assertNotNull(secretKey, "Secret key is null!");
        
        ObjectMapper objectMapper = new ObjectMapper();
        String email = input_email;
        String plain_pin = input_pin;
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", email);
        String Pin = signatureCreate.encryptAES256(plain_pin, secretKey1);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
     // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey1);
        
        jsonBody.put("signature", requestSignature);
        
        
     // Send request
        Response response2 = given()
                .header("X-GEO-LOCATION", "12,12")
                .header("X-DEVICE-ID", requestDeviceId)
                .header("User-Agent", "TravelApp/1.0.0 android")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        AuthToken = response2.getHeader("X-AUTH-TOKEN");
	    String secretKey2 = response2.jsonPath().getString("sessionKey");
	}
	
	
	
	
	@Test
	public void GetSessionInformation() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-05-13 14:38:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-CREDENTIAL","")
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-05-13 14:38:00")
            .when()
            .get("/session")
        .then()
            .statusCode(200)
            .extract().response();
        
        response.prettyPrint();
        
        String signature = response.jsonPath().getString("signature");
        String sessionKey = response.jsonPath().getString("sessionKey");
        String deviceId = response.jsonPath().getString("deviceId");
        String fullName = response.jsonPath().getString("profile.fullName");
        String country = response.jsonPath().getString("profile.country");
        String documentNumber = response.jsonPath().getString("profile.documentNumber");
        String documentType = response.jsonPath().getString("profile.documentType");
        String dateOfBirth = response.jsonPath().getString("profile.dateOfBirth");
        String documentExpiryDate = response.jsonPath().getString("profile.documentExpiryDate");
        String responseemail = response.jsonPath().getString("profile.email");
        String gender = response.jsonPath().getString("profile.gender");
        String status = response.jsonPath().getString("profile.status");
        String verificationStatus = response.jsonPath().getString("profile.verificationStatus");
        String loginAttemptCountPin = response.jsonPath().getString("profile.loginAttemptCountPin");
        String loginAttemptCountBiometric = response.jsonPath().getString("profile.loginAttemptCountBiometric");
        
        System.out.println(sessionKey);
        
        //check if response includes null value
        assertNotNull(signature, "signature is missing");
        assertNotNull(sessionKey, "sessionKey is missing");
        assertNotNull(deviceId, "device is missing");
        assertNotNull(fullName, "fullname is missing");
        assertNotNull(country, "country is missing");
        assertNotNull(documentNumber, "documentNumber is missing");
        assertNotNull(documentType, "documentType is missing");
        assertNotNull(dateOfBirth, "dateOfBirth is missing");
        assertNotNull(documentExpiryDate, "documentExpiryDate is missing");
        assertNotNull(responseemail, "responseemail is missing");
        assertNotNull(gender, "gender is missing");
        assertNotNull(status, "status is missing");
        assertNotNull(verificationStatus, "verificationStatus is missing");
        assertNotNull(loginAttemptCountPin, "loginAttemptCount is missing");
        assertNotNull(loginAttemptCountBiometric, "isBiometric is missing");
        
        //check is response includes empty values
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(sessionKey.isEmpty(), "sessionKey is missing");
        assertFalse(deviceId.isEmpty(), "device is missing");
        assertFalse(fullName.isEmpty(), "fullname is missing");
        assertFalse(country.isEmpty(), "country is missing");
        assertFalse(documentNumber.isEmpty(), "documentNumber is missing");
        assertFalse(documentType.isEmpty(), "documentType is missing");
        assertFalse(dateOfBirth.isEmpty(), "dateOfBirth is missing");
        assertFalse(documentExpiryDate.isEmpty(), "documentExpiryDate is missing");
        assertFalse(responseemail.isEmpty(), "responseemail is missing");
        assertFalse(gender.isEmpty(), "gender is missing");
        assertFalse(status.isEmpty(), "status is missing");
        assertFalse(verificationStatus.isEmpty(), "verificationStatus is missing");
        assertFalse(loginAttemptCountPin.isEmpty(), "loginAttemptCount is missing");
        assertFalse(loginAttemptCountBiometric.isEmpty(), "isBiometric is missing");
        
        System.out.println("Request ID: " + response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
        assertTrue(response.getHeaders().hasHeaderWithName("X-AUTH-TOKEN"));
	}
	
	@Test
	public void GetSessionwithoutDevice() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-DEVICE-ID", "")
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(400)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
		
	}
	@Test
	public void GetSessionwithoutLocation() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "")
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(400)
            .extract().response();
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
		
		
	}
	@Test
	public void GetSessionwithoutUserAgent() throws Exception {
		 baseURI = "https://visitor0.moco.com.np/visitor";
			
			String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-LOCATION", "")
	            .header("X-DEVICE-ID", requestDeviceId)
	            .header("User-Agent", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
	            .when()
	            .get("/session")
	        .then()
	            .statusCode(400)
	            .extract().response();
	        String code = response.jsonPath().getString("code");
	        String description = response.jsonPath().getString("description");
//	        // Check if the signature is not null or empty
	         assertNotNull(code, "code is missing from the response");
	         assertFalse(code.isEmpty(), "code is empty in the response");
	         assertNotNull(description, "description is missing from the response");
	         assertFalse(description.isEmpty(), "description is empty in the response");
	         //check if the code value is as per the decided
	         assertEquals(code,"GNR_PARAM_MISSING");
	         assertEquals(description,"Bad Request.");
		
	}
	
	@Test
	public void GetSessionwithoutAuth() throws Exception{
		 baseURI = "https://visitor0.moco.com.np/visitor";
			
			String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-LOCATION", "")
	            .header("X-DEVICE-ID", requestDeviceId)
	            .header("User-Agent", "TravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
	            .when()
	            .get("/session")
	        .then()
	            .statusCode(400)
	            .extract().response();
	        String code = response.jsonPath().getString("code");
	        String description = response.jsonPath().getString("description");
//	        // Check if the signature is not null or empty
	         assertNotNull(code, "code is missing from the response");
	         assertFalse(code.isEmpty(), "code is empty in the response");
	         assertNotNull(description, "description is missing from the response");
	         assertFalse(description.isEmpty(), "description is empty in the response");
	         //check if the code value is as per the decided
	         assertEquals(code,"GNR_PARAM_MISSING");
	         assertEquals(description,"Bad Request.");
	}
	
	@Test
	public void GetSessionwithoutSystemID() throws Exception {
		 baseURI = "https://visitor0.moco.com.np/visitor";
			
			String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-LOCATION", "")
	            .header("X-DEVICE-ID", requestDeviceId)
	            .header("User-Agent", "TravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
	            .when()
	            .get("/session")
	        .then()
	            .statusCode(400)
	            .extract().response();
	        String code = response.jsonPath().getString("code");
	        String description = response.jsonPath().getString("description");
//	        // Check if the signature is not null or empty
	         assertNotNull(code, "code is missing from the response");
	         assertFalse(code.isEmpty(), "code is empty in the response");
	         assertNotNull(description, "description is missing from the response");
	         assertFalse(description.isEmpty(), "description is empty in the response");
	         //check if the code value is as per the decided
	         assertEquals(code,"GNR_PARAM_MISSING");
	         assertEquals(description,"Bad Request.");
	}
	
	@Test
	public void GetSessionwithoutSignature() throws Exception {
		 baseURI = "https://visitor0.moco.com.np/visitor";
			
			String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-LOCATION", "")
	            .header("X-DEVICE-ID", requestDeviceId)
	            .header("User-Agent", "TravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE","") 
	            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
	            .when()
	            .get("/session")
	        .then()
	            .statusCode(400)
	            .extract().response();
	        String code = response.jsonPath().getString("code");
	        String description = response.jsonPath().getString("description");
//	        // Check if the signature is not null or empty
	         assertNotNull(code, "code is missing from the response");
	         assertFalse(code.isEmpty(), "code is empty in the response");
	         assertNotNull(description, "description is missing from the response");
	         assertFalse(description.isEmpty(), "description is empty in the response");
	         //check if the code value is as per the decided
	         assertEquals(code,"GNR_PARAM_MISSING");
	         assertEquals(description,"Bad Request.");
	}
	@Test
	public void GetSessionwithoutRequestTimestamp() throws Exception {
		 baseURI = "https://visitor0.moco.com.np/visitor";
			
			String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-LOCATION", "")
	            .header("X-DEVICE-ID", requestDeviceId)
	            .header("User-Agent", "TravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("X-REQUEST-TIMESTAMP","")
	            .when()
	            .get("/session")
	        .then()
	            .statusCode(400)
	            .extract().response();
	        
	        String code = response.jsonPath().getString("code");
	        String description = response.jsonPath().getString("description");
//	        // Check if the signature is not null or empty
	         assertNotNull(code, "code is missing from the response");
	         assertFalse(code.isEmpty(), "code is empty in the response");
	         assertNotNull(description, "description is missing from the response");
	         assertFalse(description.isEmpty(), "description is empty in the response");
	         //check if the code value is as per the decided
	         assertEquals(code,"GNR_PARAM_MISSING");
	         assertEquals(description,"Bad Request.");
	}
	
	@Test
	public void GetSessionwithInvalidDevice() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+"op00$"+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-DEVICE-ID", "op00$")
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(422)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid device Id found.");
		
	}
	@Test
	public void GetSessionwithInvalidLocation() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "$%")
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(422)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid Geo location found.");
	}
	@Test
	public void GetSessionwithInvalidUser() throws Exception {
        baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(422)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid user agent found.");
	}
	@Test
	public void GetSessionwithInvalidAuth() throws Exception {
        baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN","7887")
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(401)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_AUTHENTICATION_FAIL");
         assertEquals(description,"Authentication failed.");
	}
	@Test
	public void GetSessionwithInvalidSystemID() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transactisss)()(n"+""+"2025-04-30 15:16:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transactisss)()(n")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
            .when()
            .get("/session")
            .then()
            .statusCode(422)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid System ID.");
	}
	@Test
	public void GetSessionwithInvalidSignature() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE","aasa") 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 15:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(401)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_AUTHENTICATION_FAIL");
         assertEquals(description,"Authentication failed.");
	}
////	
	@Test
	public void GetSessionwithInvalidRequestTimestamp() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 25:16:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("X-REQUEST-TIMESTAMP","2025-04-30 25:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(422)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
//        // Check if the signature is not null or empty
         assertNotNull(code, "code is missing from the response");
         assertFalse(code.isEmpty(), "code is empty in the response");
         assertNotNull(description, "description is missing from the response");
         assertFalse(description.isEmpty(), "description is empty in the response");
         //check if the code value is as per the decided
         assertEquals(code,"GNR_INVALID_DATA");
         assertEquals(description,"Invalid Request Timestamp.");
		
	}
////	@Test
////	public void GetSessionwithinvalidSystemCredentials() throws Exception {
////		  baseURI = "https://visitor0.moco.com.np/visitor";
////			
////			String data = "12,12"+AuthToken+requestDeviceId+"TravelApp/1.0.0 android"+"transaction"+"qq"+"2025-04-30 11:00:00";
//////			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + X-REQUEST-TIMESTAMP
////			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
////			System.out.println(Signature);
////
////	        Response response = (Response) given()
////	            .header("X-GEO-LOCATION", "12,12")
////	            .header("X-DEVICE-ID", requestDeviceId)
////	            .header("User-Agent", "TravelApp/1.0.0 android")
////	            .header("X-AUTH-TOKEN",AuthToken)
////	            .header("X-SYSTEM-ID","transaction")
////	            .header("X-CREDENTIAL","qq")
////	            .header("X-SYSTEM-SIGNATURE",Signature) 
////	            .header("X-REQUEST-TIMESTAMP","2025-04-30 11:00:00")
////	            .when()
////	            .get("/session")
////	        .then()
////	            .statusCode(422)
////	            .extract().response();
////	        
////	        String code = response.jsonPath().getString("code");
////	        String description = response.jsonPath().getString("description");
//////	        // Check if the signature is not null or empty
////	         assertNotNull(code, "code is missing from the response");
////	         assertFalse(code.isEmpty(), "code is empty in the response");
////	         assertNotNull(description, "description is missing from the response");
////	         assertFalse(description.isEmpty(), "description is empty in the response");
////	         //check if the code value is as per the decided
////	         assertEquals(code,"GNR_INVALID_DATA");
////	         assertEquals(description,"Invalid Geo location found.");
//			
////	}
////	
	@AfterClass
	public void logout() {
		baseURI = "https://visitor0.moco.com.np/visitor";
        Response response = given()
            .header("X-GEO-LOCATION", "12,12")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-DEVICE-ID", requestDeviceId)
            .header("User-Agent", "TravelApp/1.0.0 android")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
	}
	

	}


package Authentication;
import org.testng.annotations.Test;

import OTP.signatureCreate;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class session {
	String requestDeviceId = "sandesh-thapa-app";
	String secretKey = "ABC123XYZ";
	String AuthToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzYW5kZXNodGhhcGFAbW9jby5jb20ubnAiLCJpc3MiOiJWSVNJVE9SLVNFUlZJQ0UiLCJqdGkiOiJzYW5kZXNoLXRoYXBhLWFwcCIsImlhdCI6MTc0NjAwNjY0NywiZXhwIjoxNzQ2MDEwMjQ3fQ.p_hXnJEQRS1R5gcGWE7DogFnxKT6xZHfkOnFL2cbpADPzJqGPZ9WgcYmejp6jckg";
	@Test
	public void GetSessionInformation() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 15:16:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 15:16:00")
            .when()
            .get("/session")
        .then()
            .statusCode(200)
            .extract().response();
        
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
	}
	
	@Test
	public void GetSessionwithoutDevice() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", "")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 15:16:00")
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
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 15:16:00")
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
			
			String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("Request-Timestamp","2025-04-30 15:16:00")
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
			
			String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN","")
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("Request-Timestamp","2025-04-30 15:16:00")
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
			
			String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("Request-Timestamp","2025-04-30 15:16:00")
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
			
			String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE","") 
	            .header("Request-Timestamp","2025-04-30 15:16:00")
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
			
			String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-Location", "")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("Request-Timestamp","")
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
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "")
            .header("X-Device-Id", "op00$")
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 15:16:00")
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
	public void GetSessionwithInvalidLocation() throws Exception {
		baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "$%")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 15:16:00")
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
	public void GetSessionwithInvalidUser() throws Exception {
        baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "12,12")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 15:16:00")
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
	public void GetSessionwithInvalidAuth() throws Exception {
        baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "$%")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 15:16:00")
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
	public void GetSessionwithInvalidSystemID() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "$%")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transactisss)()(n")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 15:16:00")
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
	public void GetSessionwithInvalidSignature() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "$%")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE","aasa") 
            .header("Request-Timestamp","2025-04-30 15:16:00")
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
         assertEquals(description,"Authentication Failed.");
	}
	
	@Test
	public void GetSessionwithInvalidRequestTimestamp() throws Exception {
         baseURI = "https://visitor0.moco.com.np/visitor";
		
		String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//		: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
		String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
		System.out.println(Signature);

        Response response = (Response) given()
            .header("X-GEO-Location", "$%")
            .header("X-Device-Id", requestDeviceId)
            .header("User-Agent", "NepalTravelApp/1.0.0 android")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-SYSTEM-ID","transaction")
            .header("X-SYSTEM-SIGNATURE",Signature) 
            .header("Request-Timestamp","2025-04-30 25:16:00")
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
         assertEquals(description,"Invalid Data.");
		
	}
	@Test
	public void GetSessionwithinvalidSystemCredentials() throws Exception {
		  baseURI = "https://visitor0.moco.com.np/visitor";
			
			String data = "12,12"+AuthToken+requestDeviceId+"NepalTravelApp/1.0.0 android"+"transaction"+""+"2025-04-30 11:00:00";
//			: X-GEO-LOCATION+ X-AUTH-TOKEN + X-DEVICE-ID+ User-Agent + X-SYSTEM-ID+ X-CREDENTIAL(Optional) + Request-Timestamp
			String Signature = signatureCreate.generateHMACSHA256(data, secretKey);
			System.out.println(Signature);

	        Response response = (Response) given()
	            .header("X-GEO-Location", "$%")
	            .header("X-Device-Id", requestDeviceId)
	            .header("User-Agent", "NepalTravelApp/1.0.0 android")
	            .header("X-AUTH-TOKEN",AuthToken)
	            .header("X-SYSTEM-ID","transaction")
	            .header("X-SYSTEM-SIGNATURE",Signature) 
	            .header("Request-Timestamp","2025-04-30 11:00:00")
	            .when()
	            .get("/session")
	        .then()
	            .statusCode(422)
	            .extract().response();
	        
	        String code = response.jsonPath().getString("code");
	        String description = response.jsonPath().getString("description");
//	        // Check if the signature is not null or empty
	         assertNotNull(code, "code is missing from the response");
	         assertFalse(code.isEmpty(), "code is empty in the response");
	         assertNotNull(description, "description is missing from the response");
	         assertFalse(description.isEmpty(), "description is empty in the response");
	         //check if the code value is as per the decided
	         assertEquals(code,"GNR_INVALID_DATA");
	         assertEquals(description,"Invalid Data.");
			
	}

	}


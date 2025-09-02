package Profile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class document {
//	
	String AuthToken;
	String requestDeviceId = "visitor-app-device"; 
	String input_email = "vivek@moco.com.np";
	String input_pin = "147369";
	@BeforeClass
	public void getToken() throws Exception{
		 RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
	        
	        Response response1 = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
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
	                .header("X-GEO-Location", "12,12")
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .contentType("application/json")
	                .body(jsonBody)
	            .when()
	                .post("/authenticate")
	            .then()
	                .statusCode(200)
	                .log().all()
	                .extract().response();
	        AuthToken = response2.getHeader("X-AUTH-TOKEN");
	        //String secretKey = response2.jsonPath().getString("sessionKey");
	}

//	@Test
//	public void uploaddocumentwithInvalidGeo() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "1212")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(422)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	//String signature = response.jsonPath().getString("signature");
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	//assertNotNull(signature,"signature is missing");
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	//assertFalse(signature.isEmpty(),"signature is empty");
//
//	assertEquals(code,"GNR_INVALID_DATA");
//	assertEquals(description,"Invalid Geo location found.");
//    }
//   }
//
//	
//
//	@Test
//	public void uploaddocumentwithInvalidDevice() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", "moco-travel-@app")
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(422)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	//String signature = response.jsonPath().getString("signature");
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	//assertNotNull(signature,"signature is missing");
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	//assertFalse(signature.isEmpty(),"signature is empty");
//
//	assertEquals(code,"GNR_INVALID_DATA");
//	assertEquals(description,"Invalid device Id found.");
//    }
//	}
//
//	@Test
//	public void uploaddocumentwithInvalidUserAgent() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(422)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	//String signature = response.jsonPath().getString("signature");
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	//assertNotNull(signature,"signature is missing");
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	//assertFalse(signature.isEmpty(),"signature is empty");
//
//	assertEquals(code,"GNR_INVALID_DATA");
//	assertEquals(description,"Invalid user agent found.");
//    }
//
//	}
//	@Test
//	public void uploaddocumentwithInvalidAuth() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "0((0")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(401)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	//String signature = response.jsonPath().getString("signature");
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	//assertNotNull(signature,"signature is missing");
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	//assertFalse(signature.isEmpty(),"signature is empty");
//
//	assertEquals(code,"GNR_AUTHENTICATION_FAIL");
//	assertEquals(description,"Authentication Failed.");
//    }
//	}
//
//	@Test
//	public void uploaddocumentwithEmptyDevice() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", "")
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(400)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	
//
//	assertEquals(code,"GNR_PARAM_MISSING");
//	assertEquals(description,"Bad Request.");
//    }
//	}
//
//	@Test
//	public void uploaddocumentwithEmptyLocation() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(400)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	
//
//	assertEquals(code,"GNR_PARAM_MISSING");
//	assertEquals(description,"Bad Request.");
//    }
//
//	}
//	@Test
//	public void uploaddocumentwithEmptyUserAgent() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(400)
//	                .extract().response();
//
//	        
//	        String code = response.jsonPath().getString("code");
//	    	String description = response.jsonPath().getString("description");
//	    	
//
//	    	assertNotNull(description, "Description is missing from the response");
//	    	assertNotNull(code, "Code is missing");
//	    	
//
//	    	assertFalse(description.isEmpty(), "Description is empty");
//	    	assertFalse(code.isEmpty(), "Code is empty");
//	    	
//
//	    	assertEquals(code,"GNR_PARAM_MISSING");
//	    	assertEquals(description,"Bad Request.");
//    }
//	}
//	@Test
//	public void uploaddocumentwithEmptyAuth() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(400)
//	                .extract().response();
//
//	        
//	        String code = response.jsonPath().getString("code");
//	    	String description = response.jsonPath().getString("description");
//	    	
//
//	    	assertNotNull(description, "Description is missing from the response");
//	    	assertNotNull(code, "Code is missing");
//	    	
//
//	    	assertFalse(description.isEmpty(), "Description is empty");
//	    	assertFalse(code.isEmpty(), "Code is empty");
//	    	
//
//	    	assertEquals(code,"GNR_PARAM_MISSING");
//	    	assertEquals(description,"Bad Request.");
//    }
//	}
//
//	@Test
//	public void uploaddocumentwithinvalidImage() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/MOCO QR Logo.png");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", AuthToken)
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "MOCO QR Logo.png", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(422)
//	                .extract().response();
//
//	        
//	        String code = response.jsonPath().getString("code");
//	    	String description = response.jsonPath().getString("description");
//	    	
//
//	    	assertNotNull(description, "Description is missing from the response");
//	    	assertNotNull(code, "Code is missing");
//	    	
//
//	    	assertFalse(description.isEmpty(), "Description is empty");
//	    	assertFalse(code.isEmpty(), "Code is empty");
//	    	
//
//	    	assertEquals(code,"GNR_INVALID_DATA");
//	    	assertEquals(description,"Uploaded file is not a .jpeg image.");
//    }
//	}

//	@Test
//	public void uploaddocumentwithLargefile() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/lareg.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "lareg.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(422)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	String signature = response.jsonPath().getString("signature");
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	assertNotNull(signature,"signature is missing");
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	assertFalse(signature.isEmpty(),"signature is empty");
//
//	assertEquals(code,"GNR_OK");
//	assertEquals(description,"Successfully verified Portrait/document.");
//    }
//
//	}
//
//	
//	@Test
//	public void uploaddocumentwhileServerdown() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(500)
//	                .extract().response();
//
//	        
//	        String code = response.jsonPath().getString("code");
//	    	String description = response.jsonPath().getString("description");
//	    	
//
//	    	assertNotNull(description, "Description is missing from the response");
//	    	assertNotNull(code, "Code is missing");
//	    	
//
//	    	assertFalse(description.isEmpty(), "Description is empty");
//	    	assertFalse(code.isEmpty(), "Code is empty");
//	    	
//
//	    	assertEquals(code,"VST_PROFILE_IMG_FACE_FEATURES ");
//	    	assertEquals(description,"Successfully verified Portrait/document.");
//    }
//	}
//	
//	@Test
//	public void uploaddocumentforsameperson() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(403)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	String signature = response.jsonPath().getString("signature");
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	assertNotNull(signature,"signature is missing");
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	assertFalse(signature.isEmpty(),"signature is empty");
//
//	assertEquals(code,"GNR_FORBIDDEN");
//	assertEquals(description,"Successfully verified Portrait/document.");
//    }
//	}
//	@Test
//	public void uploaddocumentwithvalidllCredentails() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!document.exists()) {
//	        System.out.println("File not found: " + document.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(document)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", "AuthToken")
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/document")
//	                .then()
//	                .statusCode(200)
//	                .extract().response();
//
//	        
//	String code = response.jsonPath().getString("code");
//	String description = response.jsonPath().getString("description");
//	String signature = response.jsonPath().getString("signature");
//
//	assertNotNull(description, "Description is missing from the response");
//	assertNotNull(code, "Code is missing");
//	assertNotNull(signature,"signature is missing");
//
//	assertFalse(description.isEmpty(), "Description is empty");
//	assertFalse(code.isEmpty(), "Code is empty");
//	assertFalse(signature.isEmpty(),"signature is empty");
//
//	assertEquals(code,"GNR_OK");
//	assertEquals(description,"Successfully verified Portrait/document.");
//    }
//	}
//	
	@Test
	public void uploaddocumentdifferentphoto() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/Chinese_passport_2018-09-29 (1).jpg");

	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(document)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "AuthToken")
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "Chinese_passport_2018-09-29 (1).jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"VST_PROFILE_UNMATCHED");
	assertEquals(description,"Successfully verified Portrait/document.");
	}
}
//	
	@Test
	public void uploaddocumentwithdifferentdocument() throws Exception {
		    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

		   // File document = new File("C:/Users/Dell/Downloads/WhatsApp Image 2024-12-16 at 12.18.17.jpeg");
              File document = new File("C:/Users/Dell/Downloads/WhatsApp Image 2025-06-02 at 09.48.46.jpg");
		    if (!document.exists()) {
		        System.out.println("File not found: " + document.getAbsolutePath());
		        return;
		    }

		    // Use logging filters to compare with curl if needed
		    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

		    // Use InputStream to avoid encoding issues
		    try (FileInputStream fis = new FileInputStream(document)) {
		        Response response = given()
		                .header("X-GEO-Location", "12,12")
		                .header("X-AUTH-TOKEN", AuthToken)
		                .header("X-Device-Id", requestDeviceId)
		                .header("User-Agent", "NepalTravelApp/1.0.0 android")
		                .header("Accept", "*/*")  // matches curl default
		                .multiPart("document", "WhatsApp Image 2025-06-02 at 09.48.46.jpg", fis, "image/jpeg") // key, filename, stream, type
		                .when()
		                .post("/document")
		                .then()
		                .statusCode(422)
		                .extract().response();

		        
		        
				String code = response.jsonPath().getString("code");
			String description = response.jsonPath().getString("description");
				//String signature = response.jsonPath().getString("signature");
		
				assertNotNull(description, "Description is missing from the response");
				assertNotNull(code, "Code is missing");
				//assertNotNull(signature,"signature is missing");
		
				//assertFalse(description.isEmpty(), "Description is empty");
				assertFalse(code.isEmpty(), "Code is empty");
				//assertFalse(signature.isEmpty(),"signature is empty");
		
				assertEquals(code,"VST_PROFILE_UNMATCHED");
				assertEquals(description,"Unable to match document image with selfie. Retry document upload.");
		    }

	}
	@Test
	public void uploaddocumentwithvaliddocument() throws Exception {
		    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		  //  File document = new File("C:/Users/Dell/Downloads/WhatsApp Image 2025-05-23 at 13.19.09.jpeg");
		   //File document = new File("C:/Users/Dell/Downloads/passeport.jpeg");
              File document = new File("C:/Users/Dell/Downloads/passeport.jpeg");
		    if (!document.exists()) {
		        System.out.println("File not found: " + document.getAbsolutePath());
		        return;
		    }

		    // Use logging filters to compare with curl if needed
		    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

		    // Use InputStream to avoid encoding issues
		    try (FileInputStream fis = new FileInputStream(document)) {
		        Response response = given()
		                .header("X-GEO-Location", "12,12")
		                .header("X-AUTH-TOKEN", AuthToken)
		                .header("X-Device-Id", requestDeviceId)
		                .header("User-Agent", "NepalTravelApp/1.0.0 android")
		                .header("Accept", "*/*")  // matches curl default
		                .multiPart("document", "passeport.jpeg", fis, "image/jpeg") // key, filename, stream, type
		                .when()
		                .post("/document")
		                .then()
		                .statusCode(200)
		                .extract().response();

		        
		        
				String country = response.jsonPath().getString("country");
			    String documentExpiryDate = response.jsonPath().getString("documentExpiryDate");
				String signature = response.jsonPath().getString("signature");
				String gender = response.jsonPath().getString("gender");
				String documentType = response.jsonPath().getString("documentType");
				String documentNumber = response.jsonPath().getString("documentNumber");
				String fullName = response.jsonPath().getString("fullName");
				String dateOfBirth = response.jsonPath().getString("dateOfBirth");
		
				assertNotNull(country, "country is missing from the response");
				assertNotNull(documentExpiryDate, "documentExpiryDate is missing");
				assertNotNull(signature,"signature is missing");
				assertNotNull(gender,"gender is missing");
				assertNotNull(documentType,"documentType is missing");
				assertNotNull(documentNumber);
				assertNotNull(fullName);
				assertNotNull(dateOfBirth);

		    }

	}
//	
//	@Test
//	public void uploaddocumentwithexpireddocument() throws Exception {
//		    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		    File document = new File("C:/Users/Dell/Downloads/passeport.jpeg");;
//		  // File document = new File("C:/Users/Dell/Downloads/WhatsApp Image 2024-12-16 at 12.18.17.jpeg");
//              //File document = new File("C:/Users/Dell/Downloads/aadhar.jpg");
//		    if (!document.exists()) {
//		        System.out.println("File not found: " + document.getAbsolutePath());
//		        return;
//		    }
//
//		    // Use logging filters to compare with curl if needed
//		    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//		    // Use InputStream to avoid encoding issues
//		    try (FileInputStream fis = new FileInputStream(document)) {
//		        Response response = given()
//		                .header("X-GEO-Location", "12,12")
//		                .header("X-AUTH-TOKEN", AuthToken)
//		                .header("X-Device-Id", requestDeviceId)
//		                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//		                .header("Accept", "*/*")  // matches curl default
//		                .multiPart("document", "passeport.jpeg", fis, "image/jpeg") // key, filename, stream, type
//		                .when()
//		                .post("/document")
//		                .then()
//		                .statusCode(422)
//		                .extract().response();
//
//		        
//		        
////		        String code = response.jsonPath().getString("code");
////				String description = response.jsonPath().getString("description");
////					//String signature = response.jsonPath().getString("signature");
////			
////					assertNotNull(description, "Description is missing from the response");
////					assertNotNull(code, "Code is missing");
////					//assertNotNull(signature,"signature is missing");
////			
////					//assertFalse(description.isEmpty(), "Description is empty");
////					assertFalse(code.isEmpty(), "Code is empty");
////					//assertFalse(signature.isEmpty(),"signature is empty");
////			
////					assertEquals(code,"GNR_INVALID_DATA");
////					assertEquals(description,"Document Expired.");
//			    }
//
//		    }
	
//	@Test
//	public void uploaddocumentwithUncleardocument() throws Exception {
//		    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		    File document = new File("C:/Users/Dell/Downloads/newest.jpeg");
//		  // File document = new File("C:/Users/Dell/Downloads/WhatsApp Image 2024-12-16 at 12.18.17.jpeg");
//              //File document = new File("C:/Users/Dell/Downloads/aadhar.jpg");
//		    if (!document.exists()) {
//		        System.out.println("File not found: " + document.getAbsolutePath());
//		        return;
//		    }
//
//		    // Use logging filters to compare with curl if needed
//		    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//		    // Use InputStream to avoid encoding issues
//		    try (FileInputStream fis = new FileInputStream(document)) {
//		        Response response = given()
//		                .header("X-GEO-Location", "12,12")
//		                .header("X-AUTH-TOKEN", AuthToken)
//		                .header("X-Device-Id", requestDeviceId)
//		                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//		                .header("Accept", "*/*")  // matches curl default
//		                .multiPart("document", "newest.jpeg", fis, "image/jpeg") // key, filename, stream, type
//		                .when()
//		                .post("/document")
//		                .then()
//		                .statusCode(422)
//		                .extract().response();
//
//		        
//		        
//		        String code = response.jsonPath().getString("code");
//				String description = response.jsonPath().getString("description");
//					//String signature = response.jsonPath().getString("signature");
//			
//					assertNotNull(description, "Description is missing from the response");
//					assertNotNull(code, "Code is missing");
//					//assertNotNull(signature,"signature is missing");
//			
//					//assertFalse(description.isEmpty(), "Description is empty");
//					assertFalse(code.isEmpty(), "Code is empty");
//					//assertFalse(signature.isEmpty(),"signature is empty");
//			
//					assertEquals(code,"GNR_INVALID_DATA");
//					assertEquals(description,"Document Expired.");
//			    }
//
//		    }
//	
//	@Test
//	public void uploaddocumentwithEditeddocument() throws Exception {
//		    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		    File document = new File("C:/Users/Dell/Downloads/newest.jpeg");
//		  // File document = new File("C:/Users/Dell/Downloads/WhatsApp Image 2024-12-16 at 12.18.17.jpeg");
//              //File document = new File("C:/Users/Dell/Downloads/aadhar.jpg");
//		    if (!document.exists()) {
//		        System.out.println("File not found: " + document.getAbsolutePath());
//		        return;
//		    }
//
//		    // Use logging filters to compare with curl if needed
//		    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//		    // Use InputStream to avoid encoding issues
//		    try (FileInputStream fis = new FileInputStream(document)) {
//		        Response response = given()
//		                .header("X-GEO-Location", "12,12")
//		                .header("X-AUTH-TOKEN", AuthToken)
//		                .header("X-Device-Id", requestDeviceId)
//		                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//		                .header("Accept", "*/*")  // matches curl default
//		                .multiPart("document", "newest.jpeg", fis, "image/jpeg") // key, filename, stream, type
//		                .when()
//		                .post("/document")
//		                .then()
//		                .statusCode(422)
//		                .extract().response();
//
//		        
//		        
//		        String code = response.jsonPath().getString("code");
//				String description = response.jsonPath().getString("description");
//					//String signature = response.jsonPath().getString("signature");
//			
//					assertNotNull(description, "Description is missing from the response");
//					assertNotNull(code, "Code is missing");
//					//assertNotNull(signature,"signature is missing");
//			
//					//assertFalse(description.isEmpty(), "Description is empty");
//					assertFalse(code.isEmpty(), "Code is empty");
//					//assertFalse(signature.isEmpty(),"signature is empty");
//			
//					assertEquals(code,"GNR_INVALID_DATA");
//					assertEquals(description,"Document Expired.");
//			    }
//
//		    }


	
//	@AfterClass
//	public void logout() {
//		baseURI = "https://visitor0.moco.com.np/visitor";
//      Response response = given()
//          .header("X-GEO-Location", "12,12")
//          .header("X-AUTH-TOKEN",AuthToken)
//          .header("X-Device-Id", requestDeviceId)
//          .header("User-Agent", "NepalTravelApp/1.0.0 android")
//      .when()
//          .delete("/authenticate");
//      response.then().statusCode(200);
//          
//		
//	}
}



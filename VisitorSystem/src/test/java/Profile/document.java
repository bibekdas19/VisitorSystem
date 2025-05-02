package Profile;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.io.File;
import java.io.FileInputStream;

public class document {
	
	String AuthToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ2aXZla0Btb2NvLmNvbS5ucCIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tdHJhdmVsLWFwcCIsImlhdCI6MTc0NjE2MzYyNiwiZXhwIjoxNzQ2MTY3MjI2fQ.jyrmoCg2zjGpzX8jxKuTmZuRmMQZH9AWz4ZOWaz1ZSAV1duJNt6VnEcD-Pntpeph";
//	@BeforeClass
//	public void getToken() throws Exception{
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		//get the signOn key
//		Response keyResponse = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", "moco-travel-app")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
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
//		String requestDeviceId = "moco-travel-app";
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
//				.post("/authenticate")
//				.then()
//				.statusCode(200)
//				.log().all()
//				.extract().response();
//		AuthToken = Authresponse.getHeader("X-AUTH-TOKEN");
//
//	}

	@Test
	public void uploaddocumentwithvalidCredentails() throws Exception {
		    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

		    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
		                .header("X-Device-Id", "moco-travel-app")
		                .header("User-Agent", "NepalTravelApp/1.0.0 android")
		                .header("Accept", "*/*")  // matches curl default
		                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
		                .when()
		                .post("/document")
		                .then()
		                .statusCode(200)
		                .extract().response();

		        response.prettyPrint();
		        
				String code = response.jsonPath().getString("code");
				String description = response.jsonPath().getString("description");
				String signature = response.jsonPath().getString("signature");
		
				assertNotNull(description, "Description is missing from the response");
				assertNotNull(code, "Code is missing");
				assertNotNull(signature,"signature is missing");
		
				assertFalse(description.isEmpty(), "Description is empty");
				assertFalse(code.isEmpty(), "Code is empty");
				assertFalse(signature.isEmpty(),"signature is empty");
		
				assertEquals(code,"GNR_OK");
				assertEquals(description,"Successfully verified Portrait/document.");
		    }

	}




	@Test
	public void uploaddocumentwithInvalidGeo() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(document)) {
	        Response response = given()
	                .header("X-GEO-Location", "1212")
	                .header("X-AUTH-TOKEN", "AuthToken")
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	//String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	//assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	//assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Successfully verified Portrait/document.");
    }
   }

	

	@Test
	public void uploaddocumentwithInvalidDevice() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "moco-travel-@app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	//String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	//assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	//assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}

	@Test
	public void uploaddocumentwithInvalidUserAgent() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	//String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	//assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	//assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Successfully verified Portrait/document.");
    }

	}
	@Test
	public void uploaddocumentwithInvalidAuth() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-AUTH-TOKEN", "0((0")
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	//String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	//assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	//assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}

	@Test
	public void uploaddocumentwithEmptyDevice() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(400)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	

	assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}

	@Test
	public void uploaddocumentwithEmptyLocation() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(document)) {
	        Response response = given()
	                .header("X-GEO-Location", "")
	                .header("X-AUTH-TOKEN", "AuthToken")
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(200)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	

	assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Successfully verified Portrait/document.");
    }

	}
	@Test
	public void uploaddocumentwithEmptyUserAgent() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(400)
	                .extract().response();

	        response.prettyPrint();
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"GNR_PARAM_MISSING");
	    	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}
	@Test
	public void uploaddocumentwithEmptyAuth() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-AUTH-TOKEN", "")
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(200)
	                .extract().response();

	        response.prettyPrint();
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"GNR_PARAM_MISSING");
	    	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}

	@Test
	public void uploaddocumentwithinvalidImage() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.png");

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
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        response.prettyPrint();
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"GNR_PARAM_MISSING");
	    	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}

	@Test
	public void uploaddocumentwithLargefile() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(200)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"GNR_OK");
	assertEquals(description,"Successfully verified Portrait/document.");
    }

	}

	
	@Test
	public void uploaddocumentwhileServerdown() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(500)
	                .extract().response();

	        response.prettyPrint();
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_FACE_FEATURES ");
	    	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}
	
	@Test
	public void uploaddocumentforsameperson() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(403)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"GNR_FORBIDDEN");
	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}
	@Test
	public void uploaddocumentwithvalidllCredentails() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(200)
	                .extract().response();

	        response.prettyPrint();
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"GNR_OK");
	assertEquals(description,"Successfully verified Portrait/document.");
    }
	}
	@Test
	public void uploaddocumentdifferentphoto() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File document = new File("C:/Users/Dell/Downloads/chineses.jpg");

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
	                .header("X-Device-Id", "moco-travel-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("document", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/document")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        response.prettyPrint();
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
}



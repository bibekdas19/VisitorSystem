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

public class selfie {
	
	//String eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w";
//	@BeforeClass
//	public void getToken() throws Exception{
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		//get the signOn key
//		Response keyResponse = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", "sandesh-thapa-app")
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
//		String requestDeviceId = "sandesh-thapa-app";
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
//		eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w = Authresponse.getHeader("X-AUTH-TOKEN");
//
//	}

//	@Test
//	public void uploadSelfiewithvalidCredentails() throws Exception {
//		    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//		    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//		    if (!selfie.exists()) {
//		        System.out.println("File not found: " + selfie.getAbsolutePath());
//		        return;
//		    }
//
//		    // Use logging filters to compare with curl if needed
//		    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//		    // Use InputStream to avoid encoding issues
//		    try (FileInputStream fis = new FileInputStream(selfie)) {
//		        Response response = given()
//		                .header("X-GEO-Location", "12,12")
//		                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
//		                .header("X-Device-Id", "moco-dev")
//		                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//		                .header("Accept", "*/*")  // matches curl default
//		                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//		                .when()
//		                .post("/selfie")
//		                .then()
//		                .statusCode(200)
//		                .extract().response();
//
//		        response.prettyPrint();
//		        
//				String code = response.jsonPath().getString("code");
//				String description = response.jsonPath().getString("description");
//				String signature = response.jsonPath().getString("signature");
//		
//				assertNotNull(description, "Description is missing from the response");
//				assertNotNull(code, "Code is missing");
//				assertNotNull(signature,"signature is missing");
//		
//				assertFalse(description.isEmpty(), "Description is empty");
//				assertFalse(code.isEmpty(), "Code is empty");
//				assertFalse(signature.isEmpty(),"signature is empty");
//		
//				assertEquals(code,"GNR_OK");
//				assertEquals(description,"Successfully verified Portrait/Selfie.");
//		    }
//
//	}




	@Test
	public void uploadSelfiewithInvalidGeo() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "1212")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	assertEquals(description,"Invalid Geo location found.");
    }
   }

	

	@Test
	public void uploadSelfiewithInvalidDevice() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "moco-travel-@app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	assertEquals(description,"Invalid device Id found.");
    }
	}

	@Test
	public void uploadSelfiewithInvalidUserAgent() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	assertEquals(description,"Invalid user agent found.");
    }

	}
	@Test
	public void uploadSelfiewithInvalidAuth() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "0((0")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(401)
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
	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }
	}

	@Test
	public void uploadSelfiewithEmptyDevice() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	assertEquals(description,"Bad Request.");
    }
	}

	@Test
	public void uploadSelfiewithEmptyLocation() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	assertEquals(description,"Bad Request.");
    }

	}
	@Test
	public void uploadSelfiewithEmptyUserAgent() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	assertEquals(description,"Bad Request.");
    }
	}
	@Test
	public void uploadSelfiewithEmptyAuth() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	assertEquals(description,"Bad Request.");
    }
	}

	@Test
	public void uploadSelfiewithinvalidImage() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.png");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	

	    	assertEquals(code,"GNR_INVALID_DATA");
	    	assertEquals(description,"Uploaded file is not a .jpeg image.");
    }
	}

	@Test
	public void uploadSelfiewithLargefile() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }

	}

	@Test
	public void uploadSelfiewithFaceObstructPhoto() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_OBSTRUCTED");
	    	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }
	    

	}

	@Test
	public void uploadSelfiewithSpoofimage() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_SPOOF");
	    	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }
	}

	@Test
	public void uploadSelfiewithDarkImage() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/dark.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_SPOOF");
	    	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }

	}

	@Test
	public void uploadSelfiewithEyesClosed() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_DARK");
	    	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }
	}
	
	@Test
	public void uploadSelfiewithOpenedMouth() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_FACE_FEATURES ");
	    	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }
	}
	
	@Test
	public void uploadSelfiewhileServerdown() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	    	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }
	}
	
	@Test
	public void uploadSelfieforsameperson() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }
	}
	@Test
	public void uploadSelfiewithvalidllCredentails() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");

	    if (!selfie.exists()) {
	        System.out.println("File not found: " + selfie.getAbsolutePath());
	        return;
	    }

	    // Use logging filters to compare with curl if needed
	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

	    // Use InputStream to avoid encoding issues
	    try (FileInputStream fis = new FileInputStream(selfie)) {
	        Response response = given()
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2IiwiaWF0IjoxNzQ2MTc5MTM5LCJleHAiOjE3NDYxODI3Mzl9.d42Wq5TJHmnjShYerTyNumd_GxTDRH2gddfcxlK38YapXvA4DbqpXkM6Myq2zL4w")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
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
	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }
	}
}



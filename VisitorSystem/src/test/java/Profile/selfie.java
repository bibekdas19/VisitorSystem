package Profile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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

public class selfie {
	
	String AuthToken;
	 String requestDeviceId = "visitor-app-device"; 
		String input_email = "vivek@moco.com.np";
		String input_pin = "147369";
	@BeforeClass
	public void getToken() throws Exception{
RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
        
        Response response = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 ios")
            .when()
                .get("/key")
            .then()
                .statusCode(200)
                .extract().response();

        String secretKey1 = response.jsonPath().getString("signOnKey");
        assertNotNull(secretKey1, "Secret key is null!");
        
        
        
        //authenticating into the visitor services.
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", input_email);
        String Pin = signatureCreate.encryptAES256(input_pin, secretKey1);
        credentials.put("pin", Pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);
        
        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey1);
        
        jsonBody.put("signature", requestSignature);
        
        System.out.println(jsonBody);
     // Send request
        Response response1 = given()
                .header("X-GEO-Location", "12,12")
                .header("X-Device-Id", requestDeviceId)
                .header("User-Agent", "NepalTravelApp/1.0.0 ios")
                .contentType("application/json")
                .body(jsonBody)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        //assertEquals(response1.jsonPath().getString("code"),"GNR_OK");
       AuthToken = response1.getHeader("X-AUTH-TOKEN");
	}


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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", "moco-travel-@app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
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
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(401)
	                .extract().response();

	        
	String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	//String signature = response.jsonPath().getString("signature");

	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
	//assertNotNull(signature,"signature is missing");

	assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
	//assertFalse(signature.isEmpty(),"signature is empty");

	assertEquals(code,"GNR_AUTHENTICATION_FAIL");
	assertEquals(description,"Authentication Failed.");
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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", "")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(400)
	                .extract().response();

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
//
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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(400)
	                .extract().response();

	        
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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(400)
	                .extract().response();

	        
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
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(400)
	                .extract().response();

	        
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

	    File selfie = new File("C:/Users/Dell/Downloads/MOCO QR Logo.png");

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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "MOCO QR Logo.png", fis, "image/png") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"GNR_INVALID_DATA");
	    	assertEquals(description,"Uploaded file is not a jpeg image.");
    }
	}

	@Test
	public void uploadSelfiewithFaceObstructPhoto() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/obstruct.jpg");

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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "obstruct.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"GNR_INVALID_DATA");
	    	assertEquals(description,"Full face selfie not recieved. Obstructed view");
    }
//	    
//
	}
////
	@Test
	public void uploadSelfiewithSpoofimage() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/copy.jpeg");

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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "copy.jpeg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_SPOOF");
	    	assertEquals(description,"Selfie is identified as not real.");
    }
	}

	@Test
	public void uploadSelfiewithDarkImage() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/darkface.jpg");

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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "darkface.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_DARK");
	    	assertEquals(description,"Unable to extract features. Selfie is not luminous.");
    }

	}

	@Test
	public void uploadSelfiewithEyesClosed() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/TC017.jpg");

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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "TC017.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_FACE_FEATURES");
	    	assertEquals(description,"Invalid facial features action detected. Eyes closed or mouth open.");
    }
	}
	
	@Test
	public void uploadSelfiewithOpenedMouth() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

	    File selfie = new File("C:/Users/Dell/Downloads/TC_16_04.jpg");

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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "TC_16_04.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(422)
	                .extract().response();

	        
	        String code = response.jsonPath().getString("code");
	    	String description = response.jsonPath().getString("description");
	    	

	    	assertNotNull(description, "Description is missing from the response");
	    	assertNotNull(code, "Code is missing");
	    	

	    	assertFalse(description.isEmpty(), "Description is empty");
	    	assertFalse(code.isEmpty(), "Code is empty");
	    	

	    	assertEquals(code,"VST_PROFILE_IMG_FACE_FEATURES");
	    	assertEquals(description,"Invalid facial features action detected. Eyes closed or mouth open.");
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
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", requestDeviceId)
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(500)
	                .extract().response();

	        
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
	                .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzYW5kZXNodGhhcGFAbW9jby5jb20ubnAiLCJpc3MiOiJWSVNJVE9SLVNFUlZJQ0UiLCJqdGkiOiJzYW5kZXNoLXRoYXBhLWFwcCIsImlhdCI6MTc0NjQzNDYxMCwiZXhwIjoxNzQ2NDY0NjEwfQ.qzN9rDnIhBEGiqTQ4VQQYG6FJCTZ_s_6BWEDAoRC2Fe3JbIdTOHO6Yk4-h2fzKxh")
	                .header("X-Device-Id", "sandesh-thapa-app")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .header("Accept", "*/*")  // matches curl default
	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
	                .when()
	                .post("/selfie")
	                .then()
	                .statusCode(403)
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

	assertEquals(code,"GNR_FORBIDDEN");
	assertEquals(description,"Successfully verified Portrait/Selfie.");
    }

	}
//	@Test
//	public void uploadSelfiewithvalidllCredentails() throws Exception {
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//
//	    File selfie = new File("C:/Users/Dell/Downloads/chineses.jpg");
//
//	    if (!selfie.exists()) {
//	        System.out.println("File not found: " + selfie.getAbsolutePath());
//	        return;
//	    }
//
//	    // Use logging filters to compare with curl if needed
//	    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//	    // Use InputStream to avoid encoding issues
//	    try (FileInputStream fis = new FileInputStream(selfie)) {
//	        Response response = given()
//	                .header("X-GEO-Location", "12,12")
//	                .header("X-AUTH-TOKEN", AuthToken)
//	                .header("X-Device-Id", requestDeviceId)
//	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
//	                .header("Accept", "*/*")  // matches curl default
//	                .multiPart("selfie", "chineses.jpg", fis, "image/jpeg") // key, filename, stream, type
//	                .when()
//	                .post("/selfie")
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
//	assertEquals(description,"Successfully verified Portrait/Selfie.");
//    }
//	}
	
	@Test
	public void uploadSelfiewithvalidCredentails() throws Exception {
		    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";

		   // File selfie = new File("C:/Users/Dell/Downloads/portrait.jpeg");
		    File selfie = new File("C:/Users/Dell/Downloads/real_photo.jpeg");

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
		                .header("X-AUTH-TOKEN", AuthToken)
		                .header("X-Device-Id", requestDeviceId)
		                .header("User-Agent", "NepalTravelApp/1.0.0 android")
		                .header("Accept", "*/*")  // matches curl default
		                .multiPart("selfie", "real_photo.jpeg", fis, "image/jpeg") // key, filename, stream, type
		                .when()
		                .post("/selfie")
		                .then()
		                .statusCode(200)
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
		
				assertEquals(code,"GNR_OK");
				assertEquals(description,"Successfully verified Portrait/Selfie.");
		    }
		    }


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
//	}
	}




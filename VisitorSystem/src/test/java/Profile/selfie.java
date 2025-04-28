package Profile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class selfie {
	
	String AuthToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ2aXZla0Btb2NvLmNvbS5ucCIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6Im1vY28tZGV2aWNlIiwiaWF0IjoxNzQ1ODIyMjk0LCJleHAiOjE3NDU4MjU4OTR9.atfczUDppN3CDcrunc5HneyCUZaE4HYgI6TRW_XsqxnjBPIdDctI9k3LyCHwKA1j";
//	@BeforeClass
//	public void getToken() throws Exception{
//		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
//		//get the signOn key
//		Response keyResponse = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-Device-Id", "moco-device")
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
//		String requestDeviceId = "moco-device";
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
	public void uploadSelfiewithvalidCredentails() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		// Correctly access the image from the resources folder
		File imageFile = new File(getClass().getResource("Profile/images/Image_20241216_121143_255.jpeg").toURI());


	    // Check if the file exists (for debugging)
	    if (!imageFile.exists()) {
	        System.out.println("File not found: " + imageFile.getAbsolutePath());
	    }
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("selfie", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/selfie")
				.then().log().all()
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

//	@Test
//	public void uploadSelfiewithInvalidGeo() {
//		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_INVALID_DATA");
//		assertEquals(description,"Unable to process request as data is invalid.");
//
//	}
//
//	@Test
//	public void uploadSelfiewithInvalidDevice() {
//		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_INVALID_DATA");
//		assertEquals(description,"Unable to process request as data is invalid.");
//
//	}
//
//	@Test
//	public void uploadSelfiewithInvalidUserAgent() {
//		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_INVALID_DATA");
//		assertEquals(description,"Unable to process request as data is invalid.");
//
//	}
//	@Test
//	public void uploadSelfiewithInvalidAuth() {
//		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(401)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_AUTHENTICATION_FAIL");
//		assertEquals(description,"Authentication Failed.");
//	}
//
//	@Test
//	public void uploadSelfiewithEmptyDevice() {
//		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(400)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_PARAM_MISSING");
//		assertEquals(description,"Required values missing.");
//	}
//
//	@Test
//	public void uploadSelfiewithEmptyLocation() {
//		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(400)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_PARAM_MISSING");
//		assertEquals(description,"Required values missing.");
//
//	}
//	@Test
//	public void uploadSelfiewithEmptyUserAgent() {
//		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(400)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_PARAM_MISSING");
//		assertEquals(description,"Required values missing.");
//	}
//	@Test
//	public void uploadSelfiewithEmptyAuth() {
//		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(400)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_PARAM_MISSING");
//		assertEquals(description,"Required values missing.");
//	}
//
//	@Test
//	public void uploadSelfiewithinvalidImage() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_INVALID_DATA");
//		assertEquals(description,"Unable to process request as data is invalid.");
//	}
//
//	@Test
//	public void uploadSelfiewithLargefile() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"GNR_INVALID_DATA");
//		assertEquals(description,"Unable to process request as data is invalid.");
//
//	}
//
//	@Test
//	public void uploadSelfiewithFaceObstructPhoto() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"VST_PROFILE_IMG_OBSTRUCTED");
//		assertEquals(description,"Full face selfie not recieved. Obstructed view");
//
//	}
//
//	@Test
//	public void uploadSelfiewithSpoofimage() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"VST_PROFILE_IMG_SPOOF");
//		assertEquals(description,"Selfie is identified as not real.");
//	}
//
//	@Test
//	public void uploadSelfiewithDarkImage() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"VST_PROFILE_IMG_DARK");
//		assertEquals(description,"Unable to extract features. Selfie is not luminous.");
//
//	}
//
//	@Test
//	public void uploadSelfiewithEyesClosed() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//				.baseUri(baseURI)
//				.header("X-GEO-Location", "12,12")
//				.header("X-AUTH-TOKEN",AuthToken)
//				.header("X-Device-Id", "moco-device")
//				.header("User-Agent", "NepalTravelApp/1.0.0 android")
//				.multiPart("selfie", imageFile)
//				.contentType("multipart/form-data")
//
//				.when()
//				.post("/selfie")
//				.then()
//				.statusCode(422)
//				.extract().response();
//
//		String code = response.jsonPath().getString("code");
//		String description = response.jsonPath().getString("description");
//		String signature = response.jsonPath().getString("signature");
//
//		assertNotNull(description, "Description is missing from the response");
//		assertNotNull(code, "Code is missing");
//		assertNotNull(signature,"signature is missing");
//
//		assertFalse(description.isEmpty(), "Description is empty");
//		assertFalse(code.isEmpty(), "Code is empty");
//		assertFalse(signature.isEmpty(),"signature is empty");
//
//		assertEquals(code,"VST_PROFILE_IMG_FACE_FEATURES");
//	     assertEquals(description,"Invalid facial features action detected. Eyes closed or mouth open.");
//	}
//	
//	@Test
//	public void uploadSelfiewithOpenedMouth() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//       .baseUri(baseURI)
//       .header("X-GEO-Location", "12,12")
//       .header("X-AUTH-TOKEN",AuthToken)
//       .header("X-Device-Id", "moco-device")
//       .header("User-Agent", "NepalTravelApp/1.0.0 android")
//       .multiPart("selfie", imageFile)
//       .contentType("multipart/form-data")
//       
//   .when()
//       .post("/selfie")
//   .then()
//       .statusCode(422)
//       .extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"VST_PROFILE_IMG_FACE_FEATURES");
//	     assertEquals(description,"Invalid facial features action detected. Eyes closed or mouth open.");
//	}
//	
//	@Test
//	public void uploadSelfiewhileServerdown() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//       .baseUri(baseURI)
//       .header("X-GEO-Location", "12,12")
//       .header("X-AUTH-TOKEN",AuthToken)
//       .header("X-Device-Id", "moco-device")
//       .header("User-Agent", "NepalTravelApp/1.0.0 android")
//       .multiPart("selfie", imageFile)
//       .contentType("multipart/form-data")
//       
//   .when()
//       .post("/selfie")
//   .then()
//       .statusCode(500)
//       .extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_ERR");
//	     assertEquals(description,"Unable to process request.");
//	}
//	
//	@Test
//	public void uploadSelfieforsameperson() {
//		File imageFile = new File("images/Image_20241216_121143_255.png");
//		Response response = given()
//       .baseUri(baseURI)
//       .header("X-GEO-Location", "12,12")
//       .header("X-AUTH-TOKEN",AuthToken)
//       .header("X-Device-Id", "moco-device")
//       .header("User-Agent", "NepalTravelApp/1.0.0 android")
//       .multiPart("selfie", imageFile)
//       .contentType("multipart/form-data")
//       
//   .when()
//       .post("/selfie")
//   .then()
//       .statusCode(403)
//       .extract().response();
//		
//		 String code = response.jsonPath().getString("code");
//	     String description = response.jsonPath().getString("description");
//	     String signature = response.jsonPath().getString("signature");
//	     
//	     assertNotNull(description, "Description is missing from the response");
//	     assertNotNull(code, "Code is missing");
//	     assertNotNull(signature,"signature is missing");
//	     
//	     assertFalse(description.isEmpty(), "Description is empty");
//	     assertFalse(code.isEmpty(), "Code is empty");
//	     assertFalse(signature.isEmpty(),"signature is empty");
//	     
//	     assertEquals(code,"GNR_FORBIDDEN");
//	     assertEquals(description,"Selfie already verified. Proceed to next operation.");
//	}
	@Test
	public void uploadSelfiewithvalidllCredentails() throws Exception {
	    RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
	    
	    // Correctly access the image from the resources folder
	    File imageFile = new File(getClass().getResource("/Profile/images/Image_20241216_121143_255.jpeg").toURI());

	    // Debugging step to ensure the file exists
	    if (!imageFile.exists()) {
	        System.out.println("File not found: " + imageFile.getAbsolutePath());
	    } else {
	        System.out.println("File exists: " + imageFile.getAbsolutePath());
	    }
	    
	    // Print the resource URL to check if it's resolved correctly
	    System.out.println("Resource URL: " + getClass().getResource("/Profile/images/Image_20241216_121143_255.jpeg"));
	    
	    // Proceed with API request if the file exists
	    if (imageFile.exists()) {
	        Response response = given()
	                .baseUri("https://visitor0.moco.com.np/visitor")
	                .header("X-GEO-Location", "12,12")
	                .header("X-AUTH-TOKEN", AuthToken)
	                .header("X-Device-Id", "moco-device")
	                .header("User-Agent", "NepalTravelApp/1.0.0 android")
	                .multiPart("selfie", imageFile)
	                .contentType("multipart/form-data")
	                .when()
	                .post("/selfie")
	                .then().log().all()
	                .statusCode(200)
	                .extract().response();

	        // Additional debugging: print the response body
	        String responseBody = response.getBody().asString();
	        System.out.println("Response Body: " + responseBody);

	        // Handle assertions or further checks here
	    } else {
	        System.out.println("File not found, test aborted.");
	    }
	}

}

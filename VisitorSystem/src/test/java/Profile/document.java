package Profile;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import java.io.File;

public class document {
	
	String AuthToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJsZWFybmVyLmJpYmVrZGFzQGdtYWlsLmNvbSIsImlzcyI6IlZJU0lUT1ItU0VSVklDRSIsImp0aSI6InRyYXZlbC1waG9uZSIsImlhdCI6MTc0NTkwMjkwMCwiZXhwIjoxNzQ1OTA2NTAwfQ.MqDah0lryQpUI2EvwfQyZ_RHbCHTGeM8uSPVGs8cBkedGAEuE6YXQJc_cfNgyKli";
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
	public void uploaddocumentwithvalidCredentails() throws Exception {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		// Correctly access the image from the resources folder
        File document = new File("C:/Users/Dell/Downloads/Image_20241216_121143_255.jpeg");



	    // Check if the file exists (for debugging)
	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	    }
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", document)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then().log().all()
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

	@Test
	public void uploaddocumentwithInvalidGeo() {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		// Correctly access the image from the resources folder
        File document = new File("C:/Users/Dell/Downloads/Image_20241216_121143_255.jpeg");
       // Check if the file exists (for debugging)
	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	    }
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12@12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", document)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then().log().all()
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
		assertEquals(description,"Invalid data.");

	}

	@Test
	public void uploaddocumentwithInvalidDevice() {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		// Correctly access the image from the resources folder
        File document = new File("C:/Users/Dell/Downloads/Image_20241216_121143_255.jpeg");
       // Check if the file exists (for debugging)
	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	    }
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-)device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", document)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then().log().all()
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
		assertEquals(description,"Invalid data.");

	}

	@Test
	public void uploaddocumentwithInvalidUserAgent() {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		// Correctly access the image from the resources folder
        File document = new File("C:/Users/Dell/Downloads/Image_20241216_121143_255.jpeg");
       // Check if the file exists (for debugging)
	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	    }
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "Nepal1.0.0 android")
				.multiPart("document", document)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then().log().all()
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
		assertEquals(description,"Invalid data.");

	}
	@Test
	public void uploaddocumentwithInvalidAuth() {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		// Correctly access the image from the resources folder
        File document = new File("C:/Users/Dell/Downloads/Image_20241216_121143_255.jpeg");
       // Check if the file exists (for debugging)
	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	    }
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "Nepal1.0.0 android")
				.multiPart("document", document)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then().log().all()
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
		assertEquals(description,"Invalid data.");
	}

	@Test
	public void uploaddocumentwithEmptyDevice() {
		RestAssured.baseURI = "https://visitor0.moco.com.np/visitor";
		// Correctly access the image from the resources folder
        File document = new File("C:/Users/Dell/Downloads/Image_20241216_121143_255.jpeg");
       // Check if the file exists (for debugging)
	    if (!document.exists()) {
	        System.out.println("File not found: " + document.getAbsolutePath());
	    }
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "Nepal1.0.0 android")
				.multiPart("document", document)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then().log().all()
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
		assertEquals(description,"Bad Request");
	}

	@Test
	public void uploaddocumentwithEmptyLocation() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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

		assertEquals(code,"GNR_PARAM_MISSING");
		assertEquals(description,"Required values missing.");

	}
	@Test
	public void uploaddocumentwithEmptyUserAgent() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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

		assertEquals(code,"GNR_PARAM_MISSING");
		assertEquals(description,"Required values missing.");
	}
	@Test
	public void uploaddocumentwithEmptyAuth() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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

		assertEquals(code,"GNR_PARAM_MISSING");
		assertEquals(description,"Required values missing.");
	}

	@Test
	public void uploaddocumentwithinvalidImage() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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

		assertEquals(code,"GNR_PARAM_MISSING");
		assertEquals(description,"Required values missing.");
	}

	@Test
	public void uploaddocumentwithLargefile() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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

		assertEquals(code,"GNR_PARAM_MISSING");
		assertEquals(description,"Required values missing.");

	}

	@Test
	public void uploaddocumentwithFaceObstructPhoto() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
				.extract().response();

		String code = response.jsonPath().getString("code");
		String description = response.jsonPath().getString("description");

		assertNotNull(description, "Description is missing from the response");
		assertNotNull(code, "Code is missing");
		

		assertFalse(description.isEmpty(), "Description is empty");
		assertFalse(code.isEmpty(), "Code is empty");

		assertEquals(code,"VST_PROFILE_IMG_OBSTRUCTED");
		assertEquals(description,"Full face document not recieved. Obstructed view");

	}

	@Test
	public void uploaddocumentwithSpoofimage() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
				.extract().response();

		String code = response.jsonPath().getString("code");
		String description = response.jsonPath().getString("description");
		

		assertNotNull(description, "Description is missing from the response");
		assertNotNull(code, "Code is missing");

		assertFalse(description.isEmpty(), "Description is empty");
		assertFalse(code.isEmpty(), "Code is empty");

		assertEquals(code,"VST_PROFILE_IMG_SPOOF");
		assertEquals(description,"document is identified as not real.");
	}

	@Test
	public void uploaddocumentwithDarkImage() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
				.extract().response();

		String code = response.jsonPath().getString("code");
		String description = response.jsonPath().getString("description");
		

		assertNotNull(description, "Description is missing from the response");
		assertNotNull(code, "Code is missing");

		assertFalse(description.isEmpty(), "Description is empty");
		assertFalse(code.isEmpty(), "Code is empty");

		assertEquals(code,"VST_PROFILE_IMG_DARK");
		assertEquals(description,"Unable to extract features. document is not luminous.");

	}

	@Test
	public void uploaddocumentwithEyesClosed() {
		File imageFile = new File("images/Image_20241216_121143_255.png");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

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

		assertEquals(code,"VST_PROFILE_IMG_FACE_FEATURES");
	     assertEquals(description,"Invalid facial features action detected. Eyes closed or mouth open.");
	}
	
	@Test
	public void uploaddocumentwithOpenedMouth() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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
	
	@Test
	public void uploaddocumentwhileServerdown() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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

		
	     
	     assertEquals(code,"GNR_ERR");
	     assertEquals(description,"Unable to process request.");
	}
	
	@Test
	public void uploaddocumentforsameperson() {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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
	     assertEquals(description,"document already verified. Proceed to next operation.");
	}
	@Test
	public void uploaddocumentwithvalidllCredentails() throws Exception {
		File imageFile = new File("images/Image_20241216_121143_255.jpeg");
		Response response = given()
				.baseUri(baseURI)
				.header("X-GEO-Location", "12,12")
				.header("X-AUTH-TOKEN",AuthToken)
				.header("X-Device-Id", "moco-device")
				.header("User-Agent", "NepalTravelApp/1.0.0 android")
				.multiPart("document", imageFile)
				.contentType("multipart/form-data")

				.when()
				.post("/document")
				.then()
				.statusCode(400)
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

		assertEquals(code,"GNR_PARAM_MISSING");
		assertEquals(description,"Required values missing.");
	}

}

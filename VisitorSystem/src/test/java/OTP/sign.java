package OTP;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
public class sign {
	@Test
	public void getSignature() throws Exception{
		String secretKey = "Wn1RYHB93jC8v4p+OeWQnbXf8ocWJVjc9HV+aYPSRnQ=";
		 ObjectMapper objectMapper = new ObjectMapper();
	       // String email = "vivek@moco.com.np";
	        String requestTimestamp = signatureCreate.generateTimestamp();


	        // Prepare payload without signature
	        Map<String, Object> jsonBody = new HashMap<>();
	        jsonBody.put("expMonth", "12");
	        jsonBody.put("expYear", "28");
	        jsonBody.put("requestTimestamp", requestTimestamp);

	        // Generate signature
	        String pan = signatureCreate.encryptAES256("4957030005123304", secretKey);
	        jsonBody.put("pan", pan);
	        String data = objectMapper.writeValueAsString(jsonBody);
	        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);

	        // Add signature
	        jsonBody.put("signature", requestSignature);
	        
	        System.out.println(jsonBody);
	}

}

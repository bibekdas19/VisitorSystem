package Authentication;

import static io.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import OTP.signatureCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class sessionSignatureGson {
	String sessionKey ="11d594a8f6281ab96b5a09f3bee61f1ebf5c51bfdcc5c1c0d79ca1f634fe083d";
    String CardNumber = "4440000009900010";

    @Test
    public void getSignature() throws Exception {
    	String EncryptCardNumber = signatureCreate.encryptAndUrlEncodeCard(CardNumber, sessionKey);
    	System.out.println("card number  "+ EncryptCardNumber);
       
    }
    
    @Test
    public void addCard() throws Exception {
    	String cardNumber = signatureCreate.encryptAndUrlEncodeCard(CardNumber, sessionKey);
    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Object> jsonBody = new LinkedHashMap<>();
    	jsonBody.put("cardNumber", cardNumber);
    	jsonBody.put("expiryYear", "39");
    	jsonBody.put("expiryMonth", "01");
    	jsonBody.put("nameOnCard", "SUMINA KHATIWADA");
    	jsonBody.put("cvv", "100");
    	
    	String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, sessionKey);
		
		jsonBody.put("signature", requestSignature);
		System.out.println(jsonBody);
    	
    }
    
    @Test
    public void verifyCard() throws Exception {
    	String cardNumber = signatureCreate.encryptAndUrlEncodeCard(CardNumber, sessionKey);
    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Object> jsonBody = new LinkedHashMap<>();
    	jsonBody.put("orderId", "");
    	jsonBody.put("transactionId", "");
    	jsonBody.put("cardNumber", cardNumber);
    	jsonBody.put("expiryYear", "39");
    	jsonBody.put("expiryMonth", "01");
    	jsonBody.put("nameOnCard", "SUMINA KHATIWADA");
    	jsonBody.put("cvv", "100");
    	
    	String data = objectMapper.writeValueAsString(jsonBody);
		String requestSignature = signatureCreate.generateHMACSHA256(data, sessionKey);
		
		jsonBody.put("signature", requestSignature);
		System.out.println(jsonBody);
    }
}

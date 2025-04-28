package OTP;

public class signature {
	public static void main(String []args) throws Exception {
	String json = "{\"email\":\"vivek@moco.com.np\",\"requestTimestamp\":\"2025-04-28 04:47:41\",\"otp\":\"634011\",\"pin\":\"Gom7tVLb87K/yip5etdM3w==\",\"token\":\"59KXXeFCMaUeqaIUuDwizCwiSMVY1rh0P6rqcXNiwco\"}";
	String base64Secret = "lah/bsPHMWOF4ydtaNk1wo/GHJ/bB2aRfhV0+nJIv/E=";

	String signature = signatureCreate.generateHMACSHA256(json, base64Secret);
	System.out.println("Signature: " + signature);
	}

}

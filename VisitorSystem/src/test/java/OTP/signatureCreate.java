package OTP;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.TimeZone;
//import org.apache.commons.codec.binary.Hex;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class signatureCreate {
	// Function to calculate HMAC-SHA256 hash
//    public static String generateHMACSHA256(String data, String secret) throws Exception {
//        Mac mac = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
//        mac.init(secretKey);
//        byte[] hashBytes = mac.doFinal(data.getBytes());
//        return Hex.encodeHexString(hashBytes);
//    }
//    

    public static String generateHMACSHA256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }


}

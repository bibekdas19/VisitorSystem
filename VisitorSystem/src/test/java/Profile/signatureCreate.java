package Profile;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;

public class signatureCreate {

    public static String generateTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }
//    public static String generateHMACSHA256(String data, String base64Secret) throws Exception {
//        // Decode the Base64-encoded secret key
//        byte[] decodedKey = Base64.getDecoder().decode(base64Secret);
//
//        // Initialize HMAC with SHA-256
//        Mac mac = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");
//        mac.init(secretKey);
//
//        // Generate the HMAC-SHA256 hash
//        byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
//
//        // Return Base64-encoded signature
//        return Base64.getEncoder().encodeToString(hashBytes);
//    }

    public static String generateHMACSHA256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
//    
    public static String encryptAES256(String plainText, String base64Key) throws Exception {
    	MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	byte[] keyBytes = digest.digest(base64Key.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedText;

        //        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
//        if (decodedKey.length != 32) {
//            throw new IllegalArgumentException("Invalid AES key length: must be 32 bytes for AES-256");
//        }
//
//        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, "AES");
//        //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
//
//        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
//        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public static String decryptAES256(String encryptedText, String base64Key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        if (decodedKey.length != 32) {
            throw new IllegalArgumentException("Invalid AES key length: must be 32 bytes for AES-256");
        }

        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedText);
        byte[] decrypted = cipher.doFinal(decodedEncryptedText);

        return new String(decrypted, StandardCharsets.UTF_8);
    }
}

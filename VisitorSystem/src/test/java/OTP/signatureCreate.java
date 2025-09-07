package OTP;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    public static String generatesHMACSHA256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }
    
    
    
    private static String bytesToHex(byte[] bytes) {
    	StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b); // Ensure unsigned conversion
            if (hex.length() == 1) hexString.append('0'); // Pad with leading zero
            hexString.append(hex);
        }
        return hexString.toString();
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
    
    public static String encryptCard(String text, String key) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(key.getBytes(StandardCharsets.UTF_8));

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    
     public static String encryptAndUrlEncodeCard(String plainCardNumber, String key) throws Exception {
        // Encrypt using your existing AES + Base64 method
        String encrypted = encryptCard(plainCardNumber, key);

        // Make it URL-safe
        return Base64.getUrlEncoder().withoutPadding().encodeToString(
        encrypted.getBytes(StandardCharsets.UTF_8));
    }
    
    public static String decryptAES256(String encryptedText, String base64Key) throws Exception {
    	byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Key must be 256 bits (32 bytes)");
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Decode base64-encoded input
        byte[] encryptedBytesWithIV = Base64.getDecoder().decode(encryptedText);

        // Extract IV (first 16 bytes)
        byte[] iv = Arrays.copyOfRange(encryptedBytesWithIV, 0, 16);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Extract encrypted content (remaining bytes)
        byte[] encryptedBytes = Arrays.copyOfRange(encryptedBytesWithIV, 16, encryptedBytesWithIV.length);

        // Decrypt
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
//        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
//        if (decodedKey.length != 32) {
//            throw new IllegalArgumentException("Invalid AES key length: must be 32 bytes for AES-256");
//        }
//
//        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, "AES");
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, keySpec);
//
//        byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedText);
//        byte[] decrypted = cipher.doFinal(decodedEncryptedText);
//
//        return new String(decrypted, StandardCharsets.UTF_8);
    	  
    }
    
    public static String encryptAESCard(String data, String secretKey) throws Exception {
        // 1. Hash the secretKey using SHA-256 to get a 256-bit key
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha256.digest(secretKey.getBytes("UTF-8"));

        // 2. Create AES key spec with ECB mode
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // 3. AES/ECB/PKCS5Padding (same as PKCS7 for 16-byte blocks)
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // 4. Encrypt the data and encode it as Base64
        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    public static String decryptAES(String encryptedData, String sharedKey) throws Exception {
        // 1. SHA-256 hash of the shared key
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha256.digest(sharedKey.getBytes("UTF-8"));

        // 2. Create AES key specification
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // 3. Set up AES cipher in ECB mode with PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // 4. Decode base64 input and decrypt
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedData);

        // 5. Convert to string
        return new String(decryptedBytes, "UTF-8");
    }
    
   
   
    
    
    
}

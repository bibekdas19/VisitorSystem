package KeyGeneration;

import org.testng.annotations.Test;


import OTP.signatureCreate;

public class sign {
	@Test
	public void hash() throws Exception {
		String data = "a8a95e4a-0ec9-4d31-8eea-6df922c305d8" + "void" + "success" + "2025-07-08 14:07:02";
		System.out.println("Data: " + data);
		System.out.println("Expected: ef0acd3d4cf4a569d2f41689582b4dc36dd2598e1d69a0690f4b892d8fabd91f");

		String signature = signatureCreate.generatesHMACSHA256(data, "moco@123");
		System.out.println("Generated: " + signature);

	}

}

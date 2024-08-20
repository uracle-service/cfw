package kr.co.uracle.framework.utils.cryptography;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtil {
	private final String algorithm;

	public ShaUtil (String algorithm) {
		this.algorithm = algorithm;
	}

	public String encrypt (String algorithm, String plainText) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		byte[] hash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		return hexString.toString();
	}


	public String encrypt (String plainText) throws NoSuchAlgorithmException {
		return this.encrypt(this.algorithm, plainText);
	}
}
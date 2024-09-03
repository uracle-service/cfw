package kr.co.uracle.framework.utils.cryptography;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil implements Cryptography{
	private Cipher cipher;

	public AESUtil (String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
		this.cipher = Cipher.getInstance(algorithm);

	}

	public String encrypt (String key, String iv, String plainText) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
		AlgorithmParameterSpec ivSpec;
		if( "GCM".equals(cipher.getParameters().getAlgorithm()) ){
			ivSpec = new GCMParameterSpec(128, iv.getBytes());
		}
		else{
			ivSpec = new IvParameterSpec(iv.getBytes());
		}
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

		return Base64.getEncoder()
					 .encodeToString(encrypted);
	}

	public String decrypt (String key, String iv, String encryptedText) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
		AlgorithmParameterSpec ivSpec;
		if( "GCM".equals(cipher.getParameters().getAlgorithm()) ){
			ivSpec = new GCMParameterSpec(128, iv.getBytes());
		}
		else{
			ivSpec = new IvParameterSpec(iv.getBytes());
		}
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

		byte[] decrypted = cipher.doFinal(Base64.getDecoder()
												.decode(encryptedText));

		return new String(decrypted, StandardCharsets.UTF_8);
	}
}

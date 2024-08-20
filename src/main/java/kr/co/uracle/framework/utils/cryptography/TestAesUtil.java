package kr.co.uracle.framework.utils.cryptography;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * 기본 제공 AES 암호화 모듈
 */
@Component
public class TestAesUtil implements Cryptography {

	@Value("${AES.test.algorithm}")
	private String _ALGORITHM;
	@Value("${AES.test.key}")
	private String _KEY;
	@Value("${AES.test.iv}")
	private String _IV;

	private SecretKeySpec _secretKeySpec;
	private AlgorithmParameterSpec _ivSpec;
	private Cipher cipher;

	@PostConstruct
	public void init () throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		_secretKeySpec = new SecretKeySpec(_KEY.getBytes(), "AES");

		cipher = Cipher.getInstance(_ALGORITHM);
	}

	@Override
	public String encrypt (String plainText) throws Exception {
		_ivSpec = new GCMParameterSpec(128, _IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, _secretKeySpec, _ivSpec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

		return Base64.getEncoder()
					 .encodeToString(encrypted);
	}

	@Override
	public String decrypt (String encryptedText) throws Exception {
		_ivSpec = new GCMParameterSpec(128, _IV.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, _secretKeySpec, _ivSpec);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder()
												.decode(encryptedText));

		return new String(decrypted, StandardCharsets.UTF_8);
	}
}
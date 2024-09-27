package kr.co.uracle.framework.utils.cryptography;

public interface Cryptography {
	public String encrypt(String key, String iv, String plaintext) throws Exception;
	public String decrypt(String key, String iv, String ciphertext) throws Exception;
}

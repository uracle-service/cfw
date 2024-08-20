package kr.co.uracle.framework.utils.cryptography;

public interface Cryptography {
	public String encrypt (String plainText) throws Exception;
	public String decrypt (String cipherText) throws Exception;
}
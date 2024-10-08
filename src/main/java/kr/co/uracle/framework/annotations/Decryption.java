package kr.co.uracle.framework.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.co.uracle.framework.utils.cryptography.AESUtil;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decryption {
	Class type () default AESUtil.class;
	String algorithm () default "AES/CBC/PKCS5Padding";
	String key () default "";
	String iv () default "";

}
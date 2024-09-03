package kr.co.uracle.framework.convertor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import kr.co.uracle.framework.annotations.Decryption;
import kr.co.uracle.framework.utils.cryptography.Cryptography;

@Component
public class DecrptionConvertor implements ConditionalGenericConverter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WebApplicationContext context;

	@Override
	public boolean matches (TypeDescriptor sourceType, TypeDescriptor targetType) {
		return targetType.hasAnnotation(Decryption.class);
	}

	@Override
	public Set<ConvertiblePair> getConvertibleTypes () {
		return Set.of(new ConvertiblePair(String.class, String.class));
	}

	@Override
	public Object convert (Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		Decryption decryption = targetType.getAnnotation(Decryption.class);
		if( null != decryption ){
			try {
				Class<?> clazz = decryption.type();
				Constructor<?> constructor = clazz.getConstructor(String.class);

				Cryptography decClass = (Cryptography) constructor.newInstance(decryption.algorithm());
				return decClass.decrypt(decryption.key(), decryption.iv(), source.toString());
			}
			catch (NoSuchMethodException e) {
				logger.info("복호화 중 오류 발생1 > " + e.getMessage());
				throw new RuntimeException(e);
			}
			catch (InvocationTargetException e) {
				logger.info("복호화 중 오류 발생2 > " + e.getMessage());
				throw new RuntimeException(e);
			}
			catch (InstantiationException e) {
				logger.info("복호화 중 오류 발생3 > " + e.getMessage());
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e) {
				logger.info("복호화 중 오류 발생4 > " + e.getMessage());
				throw new RuntimeException(e);
			}
			catch (Exception e) {
				logger.info("복호화 중 오류 발생5 > " + e.getMessage());
				throw new RuntimeException(e);
			}
		}
		else{
			return source;
		}
	}
}
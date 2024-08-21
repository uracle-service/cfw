package kr.co.uracle.framework.convertor;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import kr.co.uracle.framework.annotations.Decryption;
import kr.co.uracle.framework.utils.cryptography.Cryptography;

@Component
public class DecrptionConvertor implements ConditionalGenericConverter {
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
		Cryptography cryptography = (Cryptography) context.getBean(targetType.getAnnotation(Decryption.class).type());

		try {
			return cryptography.decrypt((String) source);
		}
		catch (Exception e) {
			e.printStackTrace();
			//throw new CommonException("ERROR_CODE_001", "복호화 중 오류 발생");
		}
		return source;
	}
}
package kr.co.uracle.framework.configs.aspects.commonLogging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ConditionalOnProperty(value = "common.aop.logging.enable", havingValue = "true", matchIfMissing = false)
@EnableAspectJAutoProxy //AOP 기능 활성화
public class CommonLoggingAspectsConfig {

	@Bean
	public CommonLoggingAspects commonLoggingAspects() {
		return new CommonLoggingAspects();
	}
}

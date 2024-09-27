package kr.co.uracle.framework.configs.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@ConditionalOnProperty(value = "common.swagger.enable", havingValue = "true", matchIfMissing = false)
public class SwaggerConfig {
	@Value("${commons.swagger.title}")
	private String title;

	@Value("${commons.swagger.description}")
	private String description;

	@Value("${commons.swagger.version}")
	private String version;

	@Bean
	public OpenAPI openAPI () {
		return new OpenAPI().components(new Components())
							.info(this.apiInfo());
	}

	private Info apiInfo () {
		return new Info()
			.title(this.title) // API의 제목
			.description(this.description) // API에 대한 설명
			.version(this.version); // API의 버전
	}
}

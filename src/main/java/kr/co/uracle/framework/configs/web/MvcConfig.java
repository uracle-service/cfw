package kr.co.uracle.framework.configs.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import kr.co.uracle.framework.convertor.DecrptionConvertor;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void configureViewResolvers (ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	@Override
	public void addResourceHandlers (ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:/resources/")
				.setCachePeriod(0);

		// 이미지 파일을 /images/** 경로로 매핑
		registry.addResourceHandler("/images/**")
				.addResourceLocations("classpath:/static/images/")  // 클래스패스의 static/images 폴더에서 로드
				.setCachePeriod(0);  // 1시간 동안 캐싱

		// CSS 파일을 /css/** 경로로 매핑
		registry.addResourceHandler("/css/**")
				.addResourceLocations("classpath:/static/css/")  // 클래스패스의 static/css 폴더에서 로드
				.setCachePeriod(0);  // 1시간 동안 캐싱

		// JavaScript 파일을 /js/** 경로로 매핑
		registry.addResourceHandler("/js/**")
				.addResourceLocations("classpath:/static/js/")  // 클래스패스의 static/js 폴더에서 로드
				.setCachePeriod(0);  // 1시간 동안 캐싱

		// favicon.ico 설정

		// JavaScript 파일을 /js/** 경로로 매핑
		registry.addResourceHandler("/favicon.ico")
				.addResourceLocations("classpath:/static/")  // 클래스패스의 static/js 폴더에서 로드
				.setCachePeriod(0);  // 1시간 동안 캐싱
	}

	@Override
	public void addFormatters (FormatterRegistry registry) {
		registry.addConverter(new DecrptionConvertor());
	}

	@Override
	public void extendMessageConverters (List<HttpMessageConverter<?>> converters) {
		reorderXmlConvertersToEnd(converters); //XML 컨버터 최 하위 설정
	}

	private void reorderXmlConvertersToEnd(List<HttpMessageConverter<?>> converters) {
		List<HttpMessageConverter<?>> xml = new ArrayList<>();
		for (Iterator<HttpMessageConverter<?>> iterator =
			 converters.iterator(); iterator.hasNext();) {
			HttpMessageConverter<?> converter = iterator.next();
			if ((converter instanceof AbstractXmlHttpMessageConverter<?>)
				|| (converter instanceof MappingJackson2XmlHttpMessageConverter)) {
				xml.add(converter);
				iterator.remove();
			}
		}
		converters.addAll(xml);
	}
}

package kr.co.uracle.framework.configs.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<CustomFilter> customFilter(){
		FilterRegistrationBean<CustomFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new CustomFilter());
		registerationBean.addUrlPatterns("/*");
		return registerationBean;
	}
	
}

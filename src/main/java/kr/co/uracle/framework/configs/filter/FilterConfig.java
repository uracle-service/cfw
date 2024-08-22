package kr.co.uracle.framework.configs.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.uracle.framework.utils.cryptography.Cryptography;

@Configuration
public class FilterConfig {

	@Autowired
	private HeaderConfig headerConfig;
	
	@Autowired
	@Qualifier("defaultAesUtil")
	private Cryptography aesUtil;
	
	@Bean
	public FilterRegistrationBean<CustomFilter> customFilter(){
		
		FilterRegistrationBean<CustomFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new CustomFilter(headerConfig, aesUtil));
		registerationBean.addUrlPatterns("/*");
		return registerationBean;
	}
	
}

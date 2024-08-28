package kr.co.uracle.framework.configs.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

//	@Autowired
//	private HeaderConfig headerConfig;
//	
//	@Autowired
//	@Qualifier("defaultAesUtil")
//	private Cryptography aesUtil;
	
//	@Bean
//	public FilterRegistrationBean<CustomFilter> customFilter(){
//		
//		FilterRegistrationBean<CustomFilter> registerationBean = new FilterRegistrationBean<>();
//		registerationBean.setFilter(new CustomFilter(headerConfig, aesUtil));
//		registerationBean.addUrlPatterns("/*");
//		return registerationBean;
//	}
	
	@Value("${filter.tokenUrlPatterns}")
	private String tokenUrlFilter;
	
	@Value("${filter.headerUrlPatterns}")
	private String headerUrlFilter;
	
	@Autowired
    private TokenFilter tokenFilter;

    @Autowired
    private HeaderFilter headerFilter;
    
    @Bean
    public FilterRegistrationBean<TokenFilter> tokenFilterRegistration() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(tokenFilter);
        registrationBean.addUrlPatterns(tokenUrlFilter); // Apply filter to all URLs or specific URLs
        registrationBean.setOrder(1); // Set order for execution
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<HeaderFilter> headerFilterRegistration() {
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(headerFilter);
        registrationBean.addUrlPatterns(headerUrlFilter); // Apply filter to all URLs or specific URLs
        registrationBean.setOrder(2); // Set order for execution
        return registrationBean;
    }
	
	
}

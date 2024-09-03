package kr.co.uracle.framework.configs.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	@Value("${commons.filter.customTokenFilter.urlPattern}")
	private String tokenUrlFilter;
	
	@Value("${commons.filter.headerFilter.urlPattern}")
	private String headerUrlFilter;

	@Autowired
	private MDCFilter mdcFilter;

	@Autowired
    private TokenFilter tokenFilter;

    //@Autowired
    //private HeaderFilter headerFilter;

	@Bean
	@ConditionalOnProperty(value = "common.filter.mdc.enable", havingValue = "true", matchIfMissing = false)
	public FilterRegistrationBean<MDCFilter> mdcFilterRegistration() {
		FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(mdcFilter);
		registrationBean.addUrlPatterns("*"); // Apply filter to all URLs or specific URLs
		registrationBean.setOrder(0); //무조건 최상위 실행

		return registrationBean;
	}

    @Bean
	@ConditionalOnProperty(value = "common.filter.customTokenFilter.enable", havingValue = "true", matchIfMissing = false)
    public FilterRegistrationBean<TokenFilter> tokenFilterRegistration() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(tokenFilter);
        registrationBean.addUrlPatterns(tokenUrlFilter); // Apply filter to all URLs or specific URLs
        registrationBean.setOrder(1); // Set order for execution
        return registrationBean;
    }

    //@Bean
    //public FilterRegistrationBean<HeaderFilter> headerFilterRegistration() {
    //    FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>();
    //    registrationBean.setFilter(headerFilter);
    //    registrationBean.addUrlPatterns(headerUrlFilter); // Apply filter to all URLs or specific URLs
    //    registrationBean.setOrder(2); // Set order for execution
    //    return registrationBean;
    //}
	
	
}

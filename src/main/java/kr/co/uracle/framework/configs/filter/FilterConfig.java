package kr.co.uracle.framework.configs.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class FilterConfig {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${commons.filter.customTokenFilter.urlPattern}")
	private String tokenUrlFilter;
	
	@Value("${commons.filter.headerFilter.urlPattern}")
	private String headerUrlFilter;

	/*
	@Autowired
	@Lazy
	private MDCFilter mdcFilter;

	@Autowired
	@Lazy
    private TokenFilter tokenFilter;
*/
    //@Autowired
    //private HeaderFilter headerFilter;

	@Bean
	@ConditionalOnProperty(value = "commons.filter.mdc.enable", havingValue = "true", matchIfMissing = false)
	public MDCFilter mdcFilter() {
		return new MDCFilter();
	}

	@Bean
	@ConditionalOnProperty(value = "commons.filter.mdc.enable", havingValue = "true", matchIfMissing = false)
	public FilterRegistrationBean<MDCFilter> mdcFilterRegistration(MDCFilter mdcFilter) {
		FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(mdcFilter);
		registrationBean.addUrlPatterns("*"); // Apply filter to all URLs or specific URLs
		registrationBean.setOrder(0); //무조건 최상위 실행

		return registrationBean;
	}

	@Bean
	@ConditionalOnProperty(value = "commons.filter.customTokenFilter.enable", havingValue = "true", matchIfMissing = false)
	public TokenFilter tokenFilter() {
		return new TokenFilter();
	}

	@Bean
	@ConditionalOnProperty(value = "commons.filter.customTokenFilter.enable", havingValue = "true", matchIfMissing = false)
    public FilterRegistrationBean<TokenFilter> tokenFilterRegistration(TokenFilter tokenFilter) {
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

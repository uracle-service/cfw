package kr.co.uracle.framework.configs.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.uracle.framework.model.ApiResponse;

//@Component("customTokenFilter")
public class TokenFilter implements Filter{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Value("${commons.filter.customTokenFilter.token.value}")
    private String validToken;

	@Value("${commons.filter.customTokenFilter.token.key}")
	private String tokenKey;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		//init
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader(tokenKey);
        
        if (token == null || !isValidToken(token)) {
			logger.info("Token이 올바르지 않음 [입력받은 토큰 값: {}]", token);
        	
        	httpResponse.setContentType("application/json");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            ApiResponse<Object> apiResponse = new ApiResponse<>();
            apiResponse.setStatus("9000");
            apiResponse.setData("Invalid token");
            apiResponse.setMessage("Fail");

            String jsonResponse = objectMapper.writeValueAsString(apiResponse);
            httpResponse.getWriter().write(jsonResponse);

			return; // 필터 체인의 다음 단계로 진행하지 않음
        }
		
        chain.doFilter(request, response);
		
	}
	
	private boolean isValidToken(String token) {
		return token.equals(validToken);
	}
	
	@Override
    public void destroy() {
        // destroy
    }

}

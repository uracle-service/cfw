package kr.co.uracle.framework.configs.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
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

@Component("customTokenFilter")
public class TokenFilter implements Filter{

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Value("${security.token.value}")
    private String validToken;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		//init
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("Token Filter");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader("Authorization");
        
        if (token == null || !isValidToken(token)) {
        	
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


package kr.co.uracle.framework.configs.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HeaderFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${commons.filter.headerFilter.headerName}")
    private List<String> customHeader;

	@Override
	public void init (FilterConfig filterConfig) throws ServletException {
		//init
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
//		//헤더로깅.
		logRequestHeaders(httpRequest);
		
		chain.doFilter(httpRequest, response);

	}

	private void logRequestHeaders (HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		HashMap<String, String> headerLogging = new HashMap<>();
		
		if(customHeader.size() > 0) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				Enumeration<String> headers = request.getHeaders(headerName);
				
				if(isHeaderNamePresent(headerName)) {
					while (headers.hasMoreElements()) {
						String headerValue = headers.nextElement();
						headerLogging.put(headerName, headerValue);
					}
				}
			}
			
			// headerName에 설정된 값이 있을 경우 logging처리. 
			if(headerLogging.size()>0) {
				logger.info("---------------------Request Header------------------------");
				logger.info("요청 헤더 값 : {}", headerLogging.toString());
				logger.info("-----------------------------------------------------------");
			}
			
		}
	}
	
	//설정된 로깅에 있는지 여부 체크
	public boolean isHeaderNamePresent(String headerName) {
        return customHeader.contains(headerName);
    }
	
	@Override
	public void destroy () {
		//Filter.super.destroy();
	}

}


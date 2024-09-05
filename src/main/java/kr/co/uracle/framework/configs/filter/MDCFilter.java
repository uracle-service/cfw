package kr.co.uracle.framework.configs.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class MDCFilter implements Filter {
	private static final String URACLE_TRACE_ID = "URACLE-TRACE-ID";

	@Override
	public void init (FilterConfig filterConfig) throws ServletException {
		//Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		MDC.put(URACLE_TRACE_ID, UUID.randomUUID().toString());

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		try {
			chain.doFilter(request, response);
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		finally {
			MDC.remove(URACLE_TRACE_ID);
		}
	}


	@Override
	public void destroy () {
		//Filter.super.destroy();
	}
}

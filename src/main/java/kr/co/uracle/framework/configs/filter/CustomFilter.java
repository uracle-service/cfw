package kr.co.uracle.framework.configs.filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		//init
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		System.out.println("filter 시작");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		System.out.println("여기서 뭔가 작업을 하고~");
		
		chain.doFilter(request, response);
		
		System.out.println("응답을가지고~ 여기서도 뭐 하고~");
		
		 // 수정된 응답 본문을 클라이언트에 전송
        PrintWriter writer = httpResponse.getWriter();
        writer.write("1111");
        writer.flush();
        
		System.out.println("filter 종료");
		
	}
	
}

package kr.co.uracle.framework.configs.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.uracle.framework.utils.cryptography.Cryptography;

@Component("customHeaderFilter")
public class HeaderFilter implements Filter {
	private final String URACLE_TRACE_ID = "URACLE-TRACE-ID";

	@Autowired
	private HeaderConfig headerConfig;

	@Autowired
	@Qualifier("defaultAesUtil")
	private Cryptography aesUtil;

	@Value("${headerfilter.decyptionMethod}")
	private String decyptionMethod;

	private final List<String> headerMapping = new ArrayList<>();

	public void init (HeaderConfig headerConfig) throws ServletException {
		//init
	}

	public HeaderFilter (HeaderConfig headerConfig) {
		this.headerConfig = headerConfig;

		List<String> decryptionHeaders = headerConfig.getDecyptionHeaders();

		if (decryptionHeaders != null) {
			for (String mapping : decryptionHeaders) {
				headerMapping.add(mapping);
			}
		}

	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		//요청 해더 조작
		HttpServletRequestWrapper requestWrapper = createRequestWrapper(httpRequest);

		//헤더로깅.
		logRequestHeaders(requestWrapper);

		String uracleTraceId = httpRequest.getHeader(URACLE_TRACE_ID);
		if (null == uracleTraceId || "".equals(uracleTraceId)) {
			uracleTraceId = UUID.randomUUID()
								.toString();
		}
		MDC.put(URACLE_TRACE_ID, uracleTraceId);
		try {
			chain.doFilter(requestWrapper, response);
		}
		finally {
			MDC.remove(uracleTraceId);
		}


	}

	private void logRequestHeaders (HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		System.out.println("---------------------Request Header------------------------");
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			Enumeration<String> headers = request.getHeaders(headerName);

			while (headers.hasMoreElements()) {
				String headerValue = headers.nextElement();
				System.out.println("[headerInfo]" + headerName + " => " + headerValue);
			}
		}
		System.out.println("-----------------------------------------------------------");
	}

	private HttpServletRequestWrapper createRequestWrapper (HttpServletRequest orgRequest) {
		return new HttpServletRequestWrapper(orgRequest) {

			@Override
			public String getHeader (String name) {
				String headerValue = super.getHeader(name);
				String decryptionMethod = decyptionMethod;

				if (decryptionMethod != null && headerValue != null && headerMapping.contains(name)) {
					//복호화 서비스
					try {
						return aesUtil.decrypt(headerValue);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}

				return headerValue;
			}

			@Override
			public Enumeration<String> getHeaders (String name) {
				String headerValue = super.getHeader(name);
				String decryptionMethod = decyptionMethod;

				if (decryptionMethod != null && headerValue != null && headerMapping.contains(name)) {
					try {
						return createEnumeration(aesUtil.decrypt(headerValue));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				return super.getHeaders(name);
			}

			@Override
			public Enumeration<String> getHeaderNames () {
				return super.getHeaderNames();
			}

			private Enumeration<String> createEnumeration (String headerValue) {
				return new Enumeration<>() {
					private boolean hasMore = true;

					@Override
					public boolean hasMoreElements () {
						return hasMore;
					}

					@Override
					public String nextElement () {
						if (hasMore) {
							hasMore = false;
							return headerValue;
						}
						throw new java.util.NoSuchElementException();
					}
				};
			}

		};
	}

}

package kr.co.uracle.framework.configs.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.uracle.framework.utils.cryptography.Cryptography;
import kr.co.uracle.framework.utils.cryptography.ShaUtil;

public class CustomFilter implements Filter{

//	private final Map<String, DecryptionService> decryptionServices = new HashMap<>();
    private final Map<String, String> headerDecryptionMapping = new HashMap<>();
    private ShaUtil shaUtil = null;
    
    private final Cryptography aesUtil;
	
	public CustomFilter(HeaderConfig headerConfig, Cryptography aesUtils) {

		this.aesUtil = aesUtils;
		
		List<HeaderConfig.DecryptionMapping> decryptionMappings = headerConfig.getDecryptionMappings();
		
		if (decryptionMappings != null) {
            for (HeaderConfig.DecryptionMapping mapping : decryptionMappings) {
                String method = mapping.getDecryptionMethod();

                for (String header : mapping.getHeaders()) {
                    headerDecryptionMapping.put(header, method);
                    System.out.println("[customFilter]====> header : "+ header + "  method : " + method );
                }
                
            }
        }
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		//init
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		System.out.println("filter 시작");
		
		try {
			System.out.println(aesUtil.encrypt("test"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		//요청 해더 조작
		HttpServletRequestWrapper requestWrapper = createRequestWrapper(httpRequest);
		
		//헤더로깅.
		logRequestHeaders(requestWrapper);
		
		chain.doFilter(requestWrapper, response);
		
	}
	
	@Override
	public void destroy() {
		//destroy
	}
	
	private void logRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                System.out.println("[headerName] => " + headerName + "  [headerValue] => " + headerValue);
            }
        }
    }
	
	private HttpServletRequestWrapper createRequestWrapper(HttpServletRequest orgRequest) {
		return new HttpServletRequestWrapper(orgRequest) {
			
			@Override
			public String getHeader(String name) {
				String headerValue = super.getHeader(name);
				String decryptionMethod = headerDecryptionMapping.get(name);
				
				if (decryptionMethod != null && headerValue != null) {
					//복호화 서비스
					shaUtil = new ShaUtil(decryptionMethod);
					try {
						System.out.println(headerValue);
//						return shaUtil.encrypt(decryptionMethod, headerValue);
						return aesUtil.encrypt(headerValue);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//                    DecryptionService decryptionService = decryptionServices.get(decryptionMethod);
//					return decryptionService.decrypt(headerValue);
//					return decryptionMethod + headerValue;
                }
				
                return headerValue;
			}
			
			@Override
			public Enumeration<String> getHeaders(String name) {
				String headerValue = super.getHeader(name);
                String decryptionMethod = headerDecryptionMapping.get(name);
                
                if (decryptionMethod != null && headerValue != null) {
                	//복호화 서비스
                	
                	shaUtil = new ShaUtil(decryptionMethod);
					try {
						System.out.println(headerValue);
//						return createEnumeration(shaUtil.encrypt(decryptionMethod, headerValue));
						return createEnumeration(aesUtil.encrypt(headerValue));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//                    DecryptionService decryptionService = decryptionServices.get(decryptionMethod);
//                    return createEnumeration(decryptionService.decrypt(headerValue));
//                	return createEnumeration(decryptionMethod+headerValue);
                }
                return super.getHeaders(name);
	        }
			
			@Override
            public Enumeration<String> getHeaderNames() {
				return super.getHeaderNames();
            }
			
			private Enumeration<String> createEnumeration(String headerValue) {
                return new Enumeration<>() {
                    private boolean hasMore = true;

                    @Override
                    public boolean hasMoreElements() {
                        return hasMore;
                    }

                    @Override
                    public String nextElement() {
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

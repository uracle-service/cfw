package kr.co.uracle.framework.configs.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import kr.co.uracle.framework.model.ApiResponse;

@ControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

	@Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;	//전체 controller
        return returnType.getDeclaringClass().isAnnotationPresent(RestController.class);	//RestController 만 적용.
    }

	 @Override
	    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, 
	                                 Class<? extends HttpMessageConverter<?>> selectedConverterType, 
	                                 ServerHttpRequest request, ServerHttpResponse response) {
	        // Check if the body is already wrapped
	        if (body instanceof ApiResponse) {
	            return body;
	        }

	        // Create a new ApiResponse instance
	        ApiResponse<Object> apiResponse = new ApiResponse<>();
	        apiResponse.setStatus("0000");
	        apiResponse.setData(body);
	        apiResponse.setMessage("Success"); // or set a default message

	        return apiResponse;
	    }
}

/*
package kr.co.uracle.framework.configs.advice;

import java.lang.reflect.Method;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import kr.co.uracle.framework.model.ApiResponse;

@ControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

	@Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		//return returnType.getDeclaringClass().isAnnotationPresent(RestController.class);	//RestController 만 적용.
		return true;	//전체 controller
    }

	 @Override
	 public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, 
	                                 Class<? extends HttpMessageConverter<?>> selectedConverterType, 
	                                 ServerHttpRequest request, ServerHttpResponse response) {
		 
		// 응답 body가 이미 응답래퍼로 잡혀있다면 그대로 응답.
		if (body instanceof ApiResponse) {
		    return body;
		}
		
		Method method = returnType.getMethod();
		
		//@RestController class 거나, Method가 ResponseBody 일 경우 Json으로 응답.
		boolean isRestController = (returnType.getDeclaringClass().isAnnotationPresent(RestController.class) || method.isAnnotationPresent(ResponseBody.class));
		
//		System.out.println("isRestController ==> " + isRestController);
		
		if(isRestController) {

			// Create a new ApiResponse instance
			ApiResponse<Object> apiResponse = new ApiResponse<>();
			apiResponse.setStatus("0000");
			apiResponse.setData(body);
			apiResponse.setMessage("Success"); // or set a default message
			
			return apiResponse;
		}
		
		return body;
		
	}
}
*/

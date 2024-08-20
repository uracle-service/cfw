package kr.co.uracle.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.uracle.framework.model.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(CommonException.class)
	@ResponseStatus(HttpStatus.OK)
	public Object CommonException (CommonException e, HttpServletRequest request) {
		logger.warn("의도한 익셉션 발생 {}", e.getMessage());
		System.out.println("CommonException");
		return handleException(e, request);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public Object OtherException (Exception e, HttpServletRequest request) {
		logger.warn("비정상 익셉션 발생 {}", e.getMessage());
		System.out.println("OtherException");
		return handleException(e, request);
	}
	
	// Api 요청일 경우 Json 방식 응답. 이외는 기본적으로 MVC ErrPage 응답.
	public Object handleException(Exception e, HttpServletRequest request) {
		HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler");
				
		if(handlerMethod != null) {
			
			boolean isRestController = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), RestController.class) != null
                || AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ResponseBody.class) != null;
			
			if(isRestController) {
				// REST API 방식의 응답
            	ApiResponse apiResponse = new ApiResponse("9999","fail", e.getMessage());
            	return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		//기본적으로  MVC Error 응답처리.
		request.setAttribute("errorMessage", e.getMessage());
        return "errors/commonError";
	}


}

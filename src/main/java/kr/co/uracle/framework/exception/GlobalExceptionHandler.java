package kr.co.uracle.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public String handleException (Exception e, HttpServletRequest request) {
		logger.error("비정상 오류 발생 {}", e.getMessage());
		return "errors/commonError";
	}

	@ExceptionHandler(CommonException.class)
	@ResponseStatus(HttpStatus.OK)
	public String CommonException (CommonException e, HttpServletRequest request) {
		logger.warn("의도한 익셉션 발생 {}", e.getMessage());
		return "errors/commonError";
	}


}

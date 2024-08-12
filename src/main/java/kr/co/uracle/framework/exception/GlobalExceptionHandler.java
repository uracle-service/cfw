package kr.co.uracle.framework.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, HttpServletRequest request) {
		e.printStackTrace();
		return "errors/commonError";
	}
}

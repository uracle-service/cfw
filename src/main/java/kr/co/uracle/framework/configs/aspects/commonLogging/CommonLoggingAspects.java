package kr.co.uracle.framework.configs.aspects.commonLogging;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.uracle.framework.exception.CommonException;

@Aspect
@Component
public class CommonLoggingAspects {
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) ||"
		+ "@annotation(org.springframework.web.bind.annotation.GetMapping)"
		+ "@annotation(org.springframework.web.bind.annotation.PostMapping)"
		+ "@annotation(org.springframework.web.bind.annotation.PutMapping)"
		+ "@annotation(org.springframework.web.bind.annotation.DeleteMapping)"
	)
	public void onRequest () {
	}

	@Around("onRequest()")
	public Object commonLogEvent (ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		Class clazz = joinPoint.getTarget()
							   .getClass();
		Logger logger = LoggerFactory.getLogger(clazz);

		Object parameters = this.parameterConvert(httpServletRequest, joinPoint);
		String userAgent = httpServletRequest.getHeader("User-Agent");

		Object result = null;
		long startTime = System.currentTimeMillis();
		try {
			result = joinPoint.proceed(joinPoint.getArgs());
			return result;
		}
		catch (CommonException e) {
			long endTime = System.currentTimeMillis();
			long timeInMs = endTime - startTime;

			logger.warn("[{} {}] | request: {} | exception: {} | response: {} | time: {} ms",
						httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
						parameters, e.getMessage(), result, timeInMs);
			throw e;
		}
		catch (Exception e) {

			long endTime = System.currentTimeMillis();
			long timeInMs = endTime - startTime;

			logger.error("[{} {}] | request: {} | exception: {} | response: {} | time: {} ms",
						 httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
						 parameters, e.getMessage(), result, timeInMs);
			throw e;

		}
		finally {
			long endTime = System.currentTimeMillis();
			long timeInMs = endTime - startTime;
			if (null != result) {
				logger.info("[{} {}] | request: {} | response: {} | time: {} ms",
							httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
							parameters, result, timeInMs);
			}
		}
	}

	private Object parameterConvert (HttpServletRequest httpServletRequest, JoinPoint joinPoint) throws
		JsonProcessingException {
		String contentType = httpServletRequest.getHeader("Content-Type");

		String[] parameterName = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		Object[] args = joinPoint.getArgs();

		Map<String, Object> parameters = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Object[]) {
				parameters.put(parameterName[i], Arrays.deepToString((Object[]) args[i]));
			}
			else {
				parameters.put(parameterName[i], args[i]);
			}
		}

		if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
			ObjectMapper om = new ObjectMapper();
			return om.writeValueAsString(parameters);
		}
		else {
			return parameters;
		}
	}

	@AfterReturning(pointcut = "execution(* org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice.beforeBodyWrite(..))"
		, returning = "responseBody")
	public void responseBodyLogging (Object responseBody) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.info("ResponseData: {}", responseBody);
	}
}

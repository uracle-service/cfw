package kr.co.uracle.framework.configs.aspects;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspects {
	@Pointcut("within(kr.co.uracle.*.controller..*)")
	public void request () {
	}

	@Pointcut("within(kr.co.uracle.framework.exception..*)")
	public void exception () {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping () {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMapping () {
	}

	@Around("exception()")
	public Object ErrorLogEvent (ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String userAgent = httpServletRequest.getHeader("User-Agent");

		Class clazz = joinPoint.getTarget()
							   .getClass();
		Logger logger = LoggerFactory.getLogger(clazz);

		String[] parameterName = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		Object[] args = joinPoint.getArgs();

		Map<String, Object> parameters = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			parameters.put(parameterName[i], args[i]);
		}

		try {
			return joinPoint.proceed(args);
		}
		finally {

			logger.warn("[{} {}] | exception: {} | User-Agent: {} | request: {} ",
						httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
						args[0], userAgent, httpServletRequest.getParameterMap());
		}
	}

	@Around("request()")
	public Object commonLogEvent (ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		Class clazz = joinPoint.getTarget()
							   .getClass();
		Logger logger = LoggerFactory.getLogger(clazz);
		Object result = null;

		String[] parameterName = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		Object[] args = joinPoint.getArgs();

		Map<String, Object> parameters = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			parameters.put(parameterName[i], args[i]);
		}

		long startTime = System.currentTimeMillis();
		try {
			result = joinPoint.proceed(args);
			return result;
		}
		finally {
			long endTime = System.currentTimeMillis();
			long timeInMs = endTime - startTime;

			logger.info("[{} {}] | request: {} | response: {} | time: {} ms",
						httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
						parameters, result, timeInMs);
		}
	}
}

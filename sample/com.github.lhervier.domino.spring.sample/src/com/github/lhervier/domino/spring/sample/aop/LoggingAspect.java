package com.github.lhervier.domino.spring.sample.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	/**
	 * Pointcut to detect classes we will log access
	 */
	@Pointcut("within(com.github.lhervier.domino.spring.sample.controller.*)")
	private void controller() {
	}
	
	@Around("controller()")
	public Object checkEcriturenBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
	    
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String controller = signature.getMethod().getDeclaringClass().getName();
		String method = signature.getMethod().getName();
		System.out.println("Starting method '" + method + "' on controller '" + controller + "'");
		Object result;
		result = joinPoint.proceed();
		System.out.println("End method '" + method + "' on controller '" + controller + "' (" + (System.currentTimeMillis() - start) + "ms)");
	    return result;
	}
}

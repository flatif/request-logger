package com.requestlogger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.requestlogger.MethodInvocationAppender.MethodInvocationAppenderFactory;

@Aspect
public class TraceMethodAspect {

	@Around("@annotation(traceMethodInvocation)")
	public Object addMethodInvocationInformation(ProceedingJoinPoint proceedingJoinPoint, TraceMethodInvocation traceMethodInvocation) throws Throwable{
		//TODO: serialize method parameters using some subsystem
		final MethodInvocation methodInvocation = new MethodInvocation(proceedingJoinPoint.getSignature().getName(), null);
		MethodInvocationAppenderFactory.getInstance().append(methodInvocation);
		try {
			return proceedingJoinPoint.proceed();
		}
		catch (Throwable t) {
			/*
			 * Set the failure in the method invocation chain
			 */
			throw t;
		}
	}
}

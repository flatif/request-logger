package com.requestlogger.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.requestlogger.MethodInvocation;
import com.requestlogger.MethodInvocationAppender;
import com.requestlogger.Request;
import com.requestlogger.MethodInvocationAppender.MethodInvocationAppenderFactory;

/**
 * <p>Aspect to intercept and trace a method invocation.</p>
 * <p>It use a {@link MethodInvocationAppender} to add the method call to a {@link Request}. Creating it from the {@link MethodInvocationAppenderFactory}</p>
 * @author rascioni
 *
 */
@Aspect
public class TraceMethodAspect {

	@Around("@annotation(traceMethodInvocation)")
	public Object addMethodInvocationInformation(ProceedingJoinPoint proceedingJoinPoint, TraceMethodInvocation traceMethodInvocation) throws Throwable{
		//TODO: serialize method parameters using some subsystem
		final MethodInvocation methodInvocation = new MethodInvocation(proceedingJoinPoint.getSignature().getName(), null);
		
		final MethodInvocationAppender methodInvocationAppender = MethodInvocationAppenderFactory.getInstance();
		methodInvocationAppender.append(methodInvocation);
		try {
			return proceedingJoinPoint.proceed();
		}
		catch (Throwable t) {
			/*
			 * Set the failure in the method invocation chain
			 */
			methodInvocationAppender.requestFailed(t);
			throw t;
		}
	}
}

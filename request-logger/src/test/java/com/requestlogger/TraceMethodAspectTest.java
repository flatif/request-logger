package com.requestlogger;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.requestlogger.MethodInvocationAppender.MethodInvocationAppenderFactory;
import com.requestlogger.MethodInvocationAppender.MethodInvocationAppenderFactoryStrategy;

public class TraceMethodAspectTest {
	static final String SIGNATURE_NAME = "traceMethodAspectTest";
	@Before
	public void setUp() throws Exception {
		final MethodInvocationAppender methodInvocationAppender = mock(MethodInvocationAppender.class);
		
		MethodInvocationAppenderFactory.changeFactoryStrategy(new MethodInvocationAppenderFactoryStrategy() {
			
			public MethodInvocationAppender create() {
				return methodInvocationAppender;
			}
		});
	}

	@Test
	public void testAddMethodInvocationInformation() throws Throwable {
		/*
		 * Preparing mocks
		 */
		final Signature mockSignature = mock(Signature.class);
		when(mockSignature.getName()).thenReturn(SIGNATURE_NAME);
		
		final ProceedingJoinPoint mockProceedingJoinPoint = mock(ProceedingJoinPoint.class);
		when(mockProceedingJoinPoint.getSignature()).thenReturn(mockSignature);
		/*
		 * Test execution
		 */
		TraceMethodAspect traceMethodAspect = new TraceMethodAspect();
		
		traceMethodAspect.addMethodInvocationInformation(mockProceedingJoinPoint, mock(TraceMethodInvocation.class));
		
		final MethodInvocationAppender mockMethodInvocationAppender = MethodInvocationAppenderFactory.getInstance();
		/*
		 * Verify
		 */
		ArgumentCaptor<MethodInvocation> argumentCaptor = ArgumentCaptor.forClass(MethodInvocation.class);
		verify(mockMethodInvocationAppender).append(argumentCaptor.capture());
		
		final MethodInvocation methodInvocation = argumentCaptor.getValue();
		
		assertSame(SIGNATURE_NAME, methodInvocation.methodName());
	}
	
	@After
	public void cleanUp(){
		MethodInvocationAppenderFactory.defaultFactoryStrategy();
	}

}

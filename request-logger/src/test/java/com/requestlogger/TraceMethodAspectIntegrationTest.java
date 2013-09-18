package com.requestlogger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.requestlogger.MethodInvocationAppender.MethodInvocationAppenderFactory;
import com.requestlogger.RequestExecutionResult.RequestExecutionOutcome;

public class TraceMethodAspectIntegrationTest {
	static final String SIGNATURE_NAME = "traceMethodAspectTest";
	@Before
	public void setUp() throws Exception {
		MethodInvocationAppenderFactory.getInstance().set(new TestRequest("TraceMethodAspectIntegrationTest"));
	}

	@Test
	public void testAddMethodInvocationInformation() throws Throwable {
		final ApplicationContext testContext = new AnnotationConfigApplicationContext(TraceMethodAspectIntegrationTestConfig.class);
		
		final TestController testController = testContext.getBean(TestController.class);
		
		testController.test();
		
		final Request request = MethodInvocationAppenderFactory.getInstance().clear();
		
		boolean controllerMethodTraced = false;
		boolean serviceMethodTraced = false;
		
		assertEquals(2, request.methodInvocations.size());
		
		for (MethodInvocation methodInvocation : request.methodInvocations){
			if (methodInvocation.methodName().equals("test")){
				controllerMethodTraced = true;
			}
			else if(methodInvocation.methodName().equals("doSomething")){
				serviceMethodTraced = true;
			}
			else{
				fail(String.format("Method [%s] shouldn't be in the call stack", methodInvocation));
			}
		}
		assertTrue(controllerMethodTraced && serviceMethodTraced);
	}
	
	@Test
	public void testAddMethodInvocationInformationException() throws Throwable {
		
		final ApplicationContext testContext = new AnnotationConfigApplicationContext(TraceMethodAspectIntegrationTestConfig.class);
		
		final TestController testController = testContext.getBean(TestController.class);
		
		boolean exceptionThrowed = false;
		try{
			testController.exception(SIGNATURE_NAME);
		}
		catch (Exception e) {
			exceptionThrowed = true;
		}
		
		assertTrue(exceptionThrowed);
		
		final Request request = MethodInvocationAppenderFactory.getInstance().clear();
		
		assertEquals(1, request.methodInvocations.size());
		
		assertSame(RequestExecutionOutcome.ERROR, request.executionResult.outcome);
		
		assertTrue(request.executionResult.throwable.indexOf(SIGNATURE_NAME) != -1);
		
		for (MethodInvocation methodInvocation : request.methodInvocations){
			if (!methodInvocation.methodName().equals("exception")){
				fail(String.format("Method [%s] shouldn't be in the call stack", methodInvocation));
			}
		}
	}
	
	
	@Configuration
	@EnableAspectJAutoProxy
	public static class TraceMethodAspectIntegrationTestConfig{
		@Bean
		public TraceMethodAspect traceMethodAspect(){
			return new TraceMethodAspect();
		}
		@Bean
		public TestController testController(){
			return new TestController();
		}
		@Bean
		public TestService testService(){
			return new TestServiceImpl();
		}
	}
	
	static class TestController{
		@Autowired
		private TestService testService;
		@TraceMethodInvocation
		public Object test(){
			return testService.doSomething("work");
		}
		@TraceMethodInvocation
		public Object exception(String message){
			throw new RuntimeException(message);
		}
	}
	
	static interface TestService{
		Object doSomething(String verb);
	}
	
	static class TestServiceImpl implements TestService{
		@TraceMethodInvocation
		public Object doSomething(String verb){
			return "I don't want to " + verb;
		}
	}

}

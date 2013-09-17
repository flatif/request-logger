package com.requestlogger;

import lombok.NonNull;

import com.requestlogger.MethodInvocationAppenderThreadLocal.MethodInvocationAppenderThreadLocalFactory;

public interface MethodInvocationAppender {

	public abstract void set(@NonNull Request request);

	public abstract void append(@NonNull MethodInvocation methodInvocation);

	public abstract Request clear();
	
	public static class MethodInvocationAppenderFactory{
		
		private static MethodInvocationAppenderFactoryStrategy methodInvocationAppenderFactoryStrategy = new MethodInvocationAppenderThreadLocalFactory();
		
		public static MethodInvocationAppender getInstance(){
			return methodInvocationAppenderFactoryStrategy.create();
		}
		
		static void changeFactoryStrategy(MethodInvocationAppenderFactoryStrategy methodInvocationAppenderFactoryStrategy){
			MethodInvocationAppenderFactory.methodInvocationAppenderFactoryStrategy = methodInvocationAppenderFactoryStrategy;
		}
	}
	
	static interface MethodInvocationAppenderFactoryStrategy{
		MethodInvocationAppender create();
	}

}
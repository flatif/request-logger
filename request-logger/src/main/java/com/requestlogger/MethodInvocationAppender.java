package com.requestlogger;

import lombok.NonNull;

import com.requestlogger.MethodInvocationAppenderThreadLocal.MethodInvocationAppenderThreadLocalFactory;

/**
 * <p>The {@link MethodInvocationAppender} is responsible to append all the {@link MethodInvocation} to the {@link Request} set.</p>
 * <p>This class is used to do it, in a static way (an implementation is retrievable using its factory {@link MethodInvocationAppenderFactory}).</p>
 * <p>Before start to append the {@link MethodInvocation} it needs to be set (using the {@link #set(Request)} method), and when the method call stack
 * is end the {@link Request} is retrievable using the {@link #clear()} method, that will permit the management of a new {@link Request}.</p>
 * @author rascioni
 *
 */
public interface MethodInvocationAppender {

	void set(@NonNull Request request);

	void append(@NonNull MethodInvocation methodInvocation);
	
	void requestFailed(Throwable t);

	Request clear();
	
	/**
	 * Factory class for the {@link MethodInvocationAppender}. It should return always a usable {@link MethodInvocationAppender}.
	 * The default implementation works using a {@link ThreadLocal}
	 * @author rascioni
	 *
	 */
	public static class MethodInvocationAppenderFactory{
		/*
		 * MethodInvocationAppenderFactoryStrategy was made for testing and/or if the ThreadLocal isn't good.
		 */
		private static MethodInvocationAppenderFactoryStrategy methodInvocationAppenderFactoryStrategy = new MethodInvocationAppenderThreadLocalFactory();
		
		/**
		 * Return a usable instance of the {@link MethodInvocationAppender}
		 * @return
		 */
		public static MethodInvocationAppender getInstance(){
			return methodInvocationAppenderFactoryStrategy.create();
		}
		/**
		 * <p>Change the {@link MethodInvocationAppenderFactoryStrategy}.</p>
		 * @param methodInvocationAppenderFactoryStrategy
		 */
		static void changeFactoryStrategy(MethodInvocationAppenderFactoryStrategy methodInvocationAppenderFactoryStrategy){
			MethodInvocationAppenderFactory.methodInvocationAppenderFactoryStrategy = methodInvocationAppenderFactoryStrategy;
		}
	}
	/**
	 * <p>Real implementation for the {@link MethodInvocationAppenderFactory}.</p>
	 * 
	 * @author rascioni
	 *
	 */
	static interface MethodInvocationAppenderFactoryStrategy{
		MethodInvocationAppender create();
	}

}
package com.requestlogger;

import lombok.NonNull;
/**
 * Class to add method invocations statically to a thread bounded {@link Request}
 * @author rascioni
 *
 */
public class MethodInvocationAppenderThreadLocal implements MethodInvocationAppender {

	private final static ThreadLocal<Request> REQUEST = new ThreadLocal<Request>();
	
	/* (non-Javadoc)
	 * @see com.requestlogger.MethodInvocationAppender#set(com.requestlogger.Request)
	 */
	public void set(@NonNull Request request){
		if (REQUEST.get() != null){
			throw new IllegalStateException("Can't set a new request. Clear it before set a new one");
		}
		REQUEST.set(request);
	}
	
	/* (non-Javadoc)
	 * @see com.requestlogger.MethodInvocationAppender#append(com.requestlogger.MethodInvocation)
	 */
	public void append(@NonNull MethodInvocation methodInvocation){
		if (REQUEST.get() == null){
			throw new IllegalStateException("Can't add method invocation. Have you set a Request?");
		}
		REQUEST.get().addMethodInvocation(methodInvocation);
	}
	
	/* (non-Javadoc)
	 * @see com.requestlogger.MethodInvocationAppender#clear()
	 */
	public Request clear(){
		final Request result = REQUEST.get();
		REQUEST.remove();
		return result;
	}
	
	static class MethodInvocationAppenderThreadLocalFactory implements MethodInvocationAppenderFactoryStrategy{
		public MethodInvocationAppender create() {
			return new MethodInvocationAppenderThreadLocal();
		}
	}
}

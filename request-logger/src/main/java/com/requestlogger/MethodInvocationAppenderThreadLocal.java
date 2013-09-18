package com.requestlogger;

import lombok.NonNull;
import lombok.extern.java.Log;
/**
 * <p>{@link MethodInvocationAppender} default implementation.</p>
 * <p>Class to add method invocations statically to a thread bounded {@link Request}.</p>
 * @author rascioni
 *
 */
@Log
public class MethodInvocationAppenderThreadLocal implements MethodInvocationAppender {

	private final static ThreadLocal<Request> REQUEST = new ThreadLocal<Request>();
	
	public MethodInvocationAppenderThreadLocal() {
		System.out.println("MethodInvocationAppenderThreadLocal: " + this.toString());
		System.out.println("MethodInvocationAppenderThreadLocal-> " + REQUEST.toString());
	}
	
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
	
	public void requestFailed(Throwable t) {
		if (REQUEST.get() == null){
			throw new IllegalStateException("Can't add method invocation. Have you set a Request?");
		}
		REQUEST.get().executionResult(RequestExecutionResult.error(t));
	}
	
	/* (non-Javadoc)
	 * @see com.requestlogger.MethodInvocationAppender#clear()
	 */
	public Request clear(){
		System.out.println("MethodInvocationAppenderThreadLocal: " + this.toString());
		System.out.println("MethodInvocationAppenderThreadLocal-> " + REQUEST.toString());
		final Request result = REQUEST.get();
		REQUEST.remove();
		return result;
	}
	
	@Log
	static class MethodInvocationAppenderThreadLocalFactory implements MethodInvocationAppenderFactoryStrategy{
		public MethodInvocationAppender create() {
			log.info(REQUEST.toString());
			return new MethodInvocationAppenderThreadLocal();
		}
	}
}

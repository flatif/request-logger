package com.requestlogger;

import com.requestlogger.MethodInvocationAppender.MethodInvocationAppenderFactory;
import com.requestlogger.repository.RequestRepository;

/**
 * <p>A class that extends {@code AbstractEntryPoint} represents the point from which a <em>request</em> starts to be traced.</p>
 * <p>To start tracing the extender should call the {@link #startTrace(Request)} method. When the trace is finished {@link #endTrace()}
 * should be used to persist the log.</p>
 * <p>The {@link #requestRepository()} method has to be implemented to retrieve an implementation of a {@link RequestRepository}</p>
 * @author rascioni
 *
 */
public abstract class AbstractEntryPoint {

	/**
	 * Set the {@link Request} passed in the {@link MethodInvocationAppender} retrieved from the {@link MethodInvocationAppenderFactory}.
	 * @param request
	 */
	protected void startTrace(Request request){
		MethodInvocationAppenderFactory.getInstance().set(request);
	}
	
	/**
	 * Clear and store the {@link Request} previously set in the {@link MethodInvocationAppender}
	 */
	protected void endTrace(){
		final Request request = MethodInvocationAppenderFactory.getInstance().clear();
		requestRepository().save(request);
	}
	
	protected abstract RequestRepository requestRepository();
}

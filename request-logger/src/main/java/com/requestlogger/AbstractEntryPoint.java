package com.requestlogger;

import com.requestlogger.MethodInvocationAppender.MethodInvocationAppenderFactory;

public abstract class AbstractEntryPoint {

	protected void startTrace(Request request){
		MethodInvocationAppenderFactory.getInstance().set(request);
	}
	
	protected void endTrace(){
		final Request request = MethodInvocationAppenderFactory.getInstance().clear();
		requestRepository().save(request);
	}
	
	protected abstract RequestRepository requestRepository();
}

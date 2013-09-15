package com.requestlogger;

import java.util.Date;
import java.util.List;

public abstract class Request {
	
	protected String requestId;
	
	protected UserDescriptor userDescriptor;
	
	protected Date date;
	
	protected List<MethodInvocation> methodInvocations;
	
	protected RequestExecutionResult executionResult;

	public Request(String requestId, UserDescriptor userDescriptor, Date date,
			List<MethodInvocation> methodInvocations,
			RequestExecutionResult executionResult) {
		this.requestId = requestId;
		this.userDescriptor = userDescriptor;
		this.date = date;
		this.methodInvocations = methodInvocations;
		this.executionResult = executionResult;
	}
	
	
	
}

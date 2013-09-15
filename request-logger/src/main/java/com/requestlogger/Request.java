package com.requestlogger;

import java.util.Date;
import java.util.List;

public abstract class Request {
	
	String requestId;
	
	UserDescriptor userDescriptor;
	
	Date date;
	
	List<MethodInvocation> methodInvocations;
	
	RequestExecutionResult executionResult;
	
}

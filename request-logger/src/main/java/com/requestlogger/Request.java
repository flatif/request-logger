package com.requestlogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent=true, chain=true)
@Setter
@NoArgsConstructor
@ToString
public abstract class Request {
	
	protected String requestId;
	
	protected UserDescriptor userDescriptor;
	
	protected Date date;
	
	protected final List<MethodInvocation> methodInvocations = new ArrayList<MethodInvocation>();
	
	protected RequestExecutionResult executionResult;
	
	/**
	 * Add a method invocation to the method invocation list 
	 * @param methodInvocation
	 */
	public void addMethodInvocation(MethodInvocation methodInvocation){
		this.methodInvocations.add(methodInvocation);
	}
	
}

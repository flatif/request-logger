package com.requestlogger;

public class RequestExecutionResult {
	
	public enum RequestExecutionOutcome {
		OK,
		ERROR
	}
	
	final RequestExecutionOutcome outcome;
	
	final Throwable throwable;
	
	private RequestExecutionResult(RequestExecutionOutcome outcome,
			Throwable throwable) {
		this.outcome = outcome;
		this.throwable = throwable;
	}
	
	public static RequestExecutionResult ok() {
		return new RequestExecutionResult(RequestExecutionOutcome.OK, null);
	}
	
	public static RequestExecutionResult error(Throwable t) {
		if (t == null) {
			throw new IllegalArgumentException("Must pass a non-null throwable");
		}
		return new RequestExecutionResult(RequestExecutionOutcome.ERROR, t);
	}
	
	

	

	
	
	

}

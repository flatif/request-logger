package com.requestlogger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access=AccessLevel.PACKAGE)
@ToString
public class RequestExecutionResult {
	
	public enum RequestExecutionOutcome {
		OK,
		ERROR
	}
	
	RequestExecutionOutcome outcome;
	/*
	 * Store the exception as String due problems with the java.util.StackTraceElement serialization
	 * 
	 * Do we really need to use the java.lang.Throwable class???
	 */
	String throwable;
	
	private RequestExecutionResult(RequestExecutionOutcome outcome,
			Throwable throwable) {
		this.outcome = outcome;
		if (throwable != null){
			this.throwable = toString(throwable);
		}
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

	private static String toString(Throwable t) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		t.printStackTrace(new PrintStream(baos));
		return baos.toString();
	}
}

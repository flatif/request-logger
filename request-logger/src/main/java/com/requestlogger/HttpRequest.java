package com.requestlogger;

import java.util.Date;
import java.util.List;

public class HttpRequest extends Request{
	
	public enum HttpMethod {
		GET,
		POST,
		PUT,
		DELETE,
		OPTIONS,
		HEAD,
		PATCH
	}

	private String url;
	
	private HttpMethod method;

	public HttpRequest(String requestId, UserDescriptor userDescriptor,
			Date date, List<MethodInvocation> methodInvocations,
			RequestExecutionResult executionResult, String url,
			HttpMethod method) {
		super(requestId, userDescriptor, date, methodInvocations, executionResult);
		this.url = url;
		this.method = method;
	}
	
	
}

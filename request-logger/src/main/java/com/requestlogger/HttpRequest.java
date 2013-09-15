package com.requestlogger;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent=true, chain=true)
@Setter
@NoArgsConstructor
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
}

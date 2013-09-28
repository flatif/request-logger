package com.requestlogger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent=true, chain=true)
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper=true)
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

package com.requestlogger;

import lombok.ToString;

@ToString(callSuper=true)
public class TestRequest extends Request {

	public TestRequest(String test) {
		super();
		requestId(test);
	}
}

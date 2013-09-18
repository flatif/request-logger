package com.requestlogger;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PACKAGE)
@Accessors(fluent=true)
@Getter
@ToString
public class MethodInvocation {
	
	//Full Name (package.class.methodName)
	private String methodName;
	
	// Serialized Parameters
	private List<String> parameters;

	void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	
	//TODO: May be useful to add a Date property

	
}

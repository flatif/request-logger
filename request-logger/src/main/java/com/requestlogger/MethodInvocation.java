package com.requestlogger;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(fluent=true)
@Getter
public class MethodInvocation {
	
	//Full Name (package.class.methodName)
	private String methodName;
	
	// Serialized Parameters
	private List<String> parameters; 
	
	//TODO: May be useful to add a Date property

}

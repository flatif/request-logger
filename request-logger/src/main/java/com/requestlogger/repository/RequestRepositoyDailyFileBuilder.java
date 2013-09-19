package com.requestlogger.repository;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.databind.ObjectMapper;

@NoArgsConstructor(staticName="create")
@Accessors(fluent=true, chain=true)
@Setter
public class RequestRepositoyDailyFileBuilder {

	private String fileNamePattern;
	private ObjectMapper objectMapper;
	private String logsDirectory;
	
	public RequestRepositoryDailyFile build(){
		Path directory = Paths.get(logsDirectory);
		if (!directory.toFile().exists()){
			directory.toFile().mkdirs();
		}
		return new RequestRepositoryDailyFile(Paths.get(logsDirectory), fileNamePattern, objectMapper);
	}
	
}

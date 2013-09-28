package com.requestlogger.repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

public class RequestRepositoryDailyFileFactory implements RequestRepositoryFactory{
	
	//Used yyyy-MM-dd date format to easily sort by name
	private static final String FILE_NAME_FORMAT = "request-log-{0,date,yyyy-MM-dd}.json";

	public RequestRepository create(Properties properties) {
		
		final Path logsDirectory = logsDirectory(properties);
		
		final String fileNameFormat = fileNameFormat(properties);
		
		final ObjectMapper objectMapper = objectMapper(properties);
		
		return new RequestRepositoryDailyFile(logsDirectory, fileNameFormat, objectMapper);
	}

	private ObjectMapper objectMapper(Properties properties) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY));
		return mapper;
	}

	private String fileNameFormat(Properties properties) {
		return properties.getProperty("filename.format", FILE_NAME_FORMAT);
	}

	private Path logsDirectory(Properties properties) {
		return Paths.get(properties.getProperty("logs.directory"));
	}
}

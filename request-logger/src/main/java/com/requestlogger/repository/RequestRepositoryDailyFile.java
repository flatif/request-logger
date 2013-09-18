package com.requestlogger.repository;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import lombok.SneakyThrows;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.requestlogger.Request;
import com.requestlogger.RequestRepository;

public class RequestRepositoryDailyFile implements RequestRepository {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static{
		OBJECT_MAPPER.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(Visibility.ANY));
	}

	//Used yyyy-MM-dd date format to easily sort by name
	private static final String FILE_NAME_FORMAT = "request-log-{0,date,yyyy-MM-dd}.json";

	@SneakyThrows
	public void save(Request request) {
		append(request);
	}
	
	protected File currentLog(){
		return new File(MessageFormat.format(FILE_NAME_FORMAT, new Date()));
	}
	
	protected void append(Request request) throws JsonGenerationException, JsonMappingException, IOException{
		final JsonGenerator generator = OBJECT_MAPPER.getFactory().createGenerator(currentLog(), JsonEncoding.UTF8);
		generator.useDefaultPrettyPrinter();
		generator.writeObject(request);
	}
}

package com.requestlogger.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.requestlogger.Request;

@AllArgsConstructor
public class RequestRepositoryDailyFile implements RequestRepository {
	private final Path logsDirectory;
	private final String fileNameFormat;
	private final ObjectMapper objectMapper;
	
	@SneakyThrows
	public void save(Request request) {
		append(request);
	}
	
	protected Path currentLog(){
		return logsDirectory.resolve(currentLogName());
	}

	private String currentLogName() {
		return MessageFormat.format(fileNameFormat, new Date());
	}
	
	protected void append(Request request) throws JsonGenerationException, JsonMappingException, IOException{
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		final JsonGenerator generator = objectMapper.getFactory().createGenerator(stream, JsonEncoding.UTF8);
		generator.useDefaultPrettyPrinter();
		generator.writeObject(request);
		
		appendToLogFileAsync(stream.toByteArray());
	}
	
	protected void appendToLogFileAsync(byte[] bytes) throws IOException{
		final Path logFilePath = currentLog();
		
		final ByteBuffer toWrite = ByteBuffer.wrap(bytes);
		
		FileChannel.open(logFilePath, StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE)
			.write(toWrite);
	}
	
	/* TO READ THE FILE
	  ObjectMapper mapper = RequestRepositoryDailyFile.OBJECT_MAPPER;
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(new RequestRepositoryDailyFile().currentLog());
        jsonParser.nextToken(); //position stream into first entry
        ObjectReader objReader = mapper.reader(HttpRequest.class);
        MappingIterator<HttpRequest> it = objReader.readValues(jsonParser);
        
        HttpRequest part;
        while(it.hasNext()) {
            part = it.nextValue();
            System.out.println(part);
        }
	 */
}

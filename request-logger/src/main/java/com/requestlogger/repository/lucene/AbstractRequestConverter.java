package com.requestlogger.repository.lucene;

import java.util.Date;

import lombok.AllArgsConstructor;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.requestlogger.Request;
import com.requestlogger.RequestExecutionResult;
import com.requestlogger.RequestExecutionResult.RequestExecutionOutcome;

@AllArgsConstructor
public abstract class AbstractRequestConverter<R extends Request> {
	
	protected final ObjectMapper mapper = new ObjectMapper();

	protected abstract Document convert(R request);
	
	protected Document document(Document document, Request request) throws JsonProcessingException {
		document.add(new StringField("id", request.requestId(), Store.YES));
		document.add(new StringField("executionResultOutcome", request.executionResult().getOutcome().name(), Store.YES));
		document.add(new StringField("executionResultThrowable", request.executionResult().getThrowable(), Store.YES));
		document.add(new LongField("date", request.date().getTime(), Store.YES));
		document.add(new TextField("methodInvocations", mapper.writeValueAsString(request.methodInvocations()), Store.YES));
		document.add(new StringField("userId", request.userDescriptor().getUserId(), Store.YES));
		return document;
	}
	
	protected abstract R toRequest(Document document);
	
	protected R request(Document document, R request) {
		request.date(new Date(Long.parseLong(document.get("date"))));
		request.executionResult(new RequestExecutionResult(RequestExecutionOutcome.valueOf(document.get("executionResultOutcome")), document.get("executionResultThrowable")));
		request.requestId(document.get("id"));
		return request;
	}
	
}

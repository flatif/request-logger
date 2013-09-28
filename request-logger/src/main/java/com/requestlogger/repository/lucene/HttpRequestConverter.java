package com.requestlogger.repository.lucene;

import lombok.SneakyThrows;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;

import com.requestlogger.HttpRequest;
import com.requestlogger.HttpRequest.HttpMethod;

public class HttpRequestConverter extends AbstractRequestConverter<HttpRequest> {

	@Override
	@SneakyThrows
	protected Document convert(HttpRequest request) {
		final Document document = new Document();
		document.add(new StringField("httpMethod", request.method().name(), Store.YES));
		document.add(new StringField("url", request.url(), Store.YES));
		return document(document, request);
	}

	@Override
	protected HttpRequest toRequest(Document document) {
		final HttpRequest request = new HttpRequest();
		request.method(HttpMethod.valueOf(document.get("httpMethod")));
		request.url(document.get("url"));
		return request(document, request);
	}

}

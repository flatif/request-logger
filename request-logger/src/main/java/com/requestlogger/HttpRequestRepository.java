package com.requestlogger;

public interface HttpRequestRepository {

	/**
	 * Persist the {@link HttpRequest}
	 * @param request
	 */
	void save(HttpRequest request);
}

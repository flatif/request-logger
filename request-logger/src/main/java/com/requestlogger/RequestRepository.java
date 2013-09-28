package com.requestlogger;

import java.util.List;

public interface RequestRepository {

	void save(HttpRequest request);
	
	List<HttpRequest> findAll();
}

package com.requestlogger;

import java.util.List;

public interface RequestRepository<R extends Request> {

	void save(R request);
	
	List<R> findAll();
}

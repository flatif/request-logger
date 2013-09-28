package com.requestlogger.repository;

import java.util.List;

import com.requestlogger.Request;

public interface RequestRepository<R extends Request> {

	void save(R request);
	
	List<R> findAll();
}

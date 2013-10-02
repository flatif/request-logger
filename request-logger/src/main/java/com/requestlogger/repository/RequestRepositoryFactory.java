package com.requestlogger.repository;

import java.util.Properties;

import com.requestlogger.Request;


public interface RequestRepositoryFactory<R extends Request> {

	public RequestRepository<R> create(Properties properties);
}

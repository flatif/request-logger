package com.requestlogger.repository;

import java.util.Properties;


public interface RequestRepositoryFactory {

	public RequestRepository create(Properties properties);
}

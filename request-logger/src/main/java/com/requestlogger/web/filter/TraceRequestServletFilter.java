package com.requestlogger.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.SneakyThrows;

import com.requestlogger.AbstractEntryPoint;
import com.requestlogger.HttpRequest;
import com.requestlogger.UserDescriptor;
import com.requestlogger.HttpRequest.HttpMethod;
import com.requestlogger.repository.RequestRepository;
import com.requestlogger.repository.RequestRepositoryDailyFileFactory;
import com.requestlogger.repository.RequestRepositoryFactory;

public class TraceRequestServletFilter extends AbstractEntryPoint implements Filter{
	
	private static final String DEFAULT_REPOSITORY_CLASS = RequestRepositoryDailyFileFactory.class.getName();
	private RequestRepository<HttpRequest> repository;
	
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		final HttpServletRequest httpServletRequest = ((HttpServletRequest)servletRequest);
		
		final HttpRequest request = createHttpRequestLog(httpServletRequest);
		
		startTrace(request);
		chain.doFilter(servletRequest, response);
		endTrace();
		
	}
	private HttpRequest createHttpRequestLog(
			final HttpServletRequest httpServletRequest) {
		final HttpRequest request = new HttpRequest();
		
		request.method(HttpMethod.valueOf(httpServletRequest.getMethod()));
		request.url(httpServletRequest.getRequestURL().toString());
		
		request.userDescriptor(createUserDescriptor(httpServletRequest));
		return request;
	}
	public UserDescriptor createUserDescriptor(HttpServletRequest request){
		//What should we use as description?
		return new UserDescriptor(request.getRemoteUser(), request.getUserPrincipal().toString());
	}
	public void destroy() {}
	@SneakyThrows
	public void init(FilterConfig filterConfig) throws ServletException {
		String repositoryClass = filterConfig.getInitParameter("request-repository-class");
		if (repositoryClass == null){
			repositoryClass = DEFAULT_REPOSITORY_CLASS;
		}
		repository = ((RequestRepositoryFactory) Class.forName(repositoryClass).newInstance()).create(properties(filterConfig));
	}
	@Override
	protected RequestRepository requestRepository() {
		return repository;
	}
	
	private Properties properties(FilterConfig filterConfig){
		@SuppressWarnings("unchecked")
		final Enumeration<String> params = filterConfig.getInitParameterNames();
		final Properties result = new Properties();
		while (params.hasMoreElements()){
			final String key = params.nextElement();
			result.setProperty(key, filterConfig.getInitParameter(key));
		}
		return result;
	}
}

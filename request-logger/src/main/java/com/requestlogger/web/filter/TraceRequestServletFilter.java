package com.requestlogger.web.filter;

import java.io.IOException;

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
import com.requestlogger.HttpRequest.HttpMethod;
import com.requestlogger.RequestRepository;
import com.requestlogger.repository.RequestRepositoryDailyFile;

public class TraceRequestServletFilter extends AbstractEntryPoint implements Filter{
	
	private static final String DEFAULT_REPOSITORY_CLASS = RequestRepositoryDailyFile.class.getName();
	private RequestRepository repository;
	
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
		
		return request;
	}
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@SneakyThrows
	public void init(FilterConfig filterConfig) throws ServletException {
		String repositoryClass = filterConfig.getInitParameter("request-repository-class");
		if (repositoryClass == null){
			repositoryClass = DEFAULT_REPOSITORY_CLASS;
		}
		repository = (RequestRepository) Class.forName(repositoryClass).newInstance();
	}
	@Override
	protected RequestRepository requestRepository() {
		return repository;
	}
}

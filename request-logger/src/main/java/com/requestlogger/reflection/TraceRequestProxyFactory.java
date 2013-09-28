package com.requestlogger.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.requestlogger.MethodInvocation;
import com.requestlogger.MethodInvocationAppender;
import com.requestlogger.MethodInvocationAppender.MethodInvocationAppenderFactory;

public class TraceRequestProxyFactory {

	@SuppressWarnings("unchecked")
	public static <T, I extends T> T wrap(Class<T> clazz, final I instance){
		return (T) Proxy.newProxyInstance(TraceRequestProxyFactory.class.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
			
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				final MethodInvocationAppender methodInvocationAppender = MethodInvocationAppenderFactory.getInstance();
				methodInvocationAppender.append(MethodInvocation.create(method.getName(), new String[0]));
				
				try{
					return method.invoke(instance, args);
				}
				catch (Exception e) {
					methodInvocationAppender.requestFailed(e);
					throw e;
				}
			}
		});
	}
}

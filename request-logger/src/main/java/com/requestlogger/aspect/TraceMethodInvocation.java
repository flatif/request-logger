package com.requestlogger.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation used by the {@link TraceMethodAspect} to intercept the method invocation to be traced.</p>
 * @author rascioni
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TraceMethodInvocation {

}

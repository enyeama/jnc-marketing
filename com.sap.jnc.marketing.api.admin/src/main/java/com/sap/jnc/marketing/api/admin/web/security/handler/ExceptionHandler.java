package com.sap.jnc.marketing.api.admin.web.security.handler;

public interface ExceptionHandler<TException extends Exception> {

	Class<TException> getExceptionType();

	int getStatusCode();

	Object getBody(TException ex);
}

package com.sap.jnc.marketing.api.admin.web.security.handler;

import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletResponse;

public abstract class GeneralExceptionHandler<TException extends Exception> implements ExceptionHandler<TException> {

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<TException> getExceptionType() {
		return (Class<TException>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments())[0];
	}
}

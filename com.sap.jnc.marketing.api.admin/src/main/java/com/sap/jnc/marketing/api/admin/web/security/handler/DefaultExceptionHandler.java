package com.sap.jnc.marketing.api.admin.web.security.handler;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DefaultExceptionHandler extends GeneralExceptionHandler<Exception> {

	protected static final String DEFAULT_MESSAGE = "系统无法处理您的请求，请联系管理员或者稍后再试。";

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	}

	@Override
	public Object getBody(Exception ex) {
		return DEFAULT_MESSAGE;
	}
}

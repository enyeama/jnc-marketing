package com.sap.jnc.marketing.api.admin.web.security.handler;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ExceptionContainer {

	@JsonUnwrapped
	protected Object body;

	public ExceptionContainer(Object body) {
		this.body = body;
	}

	public Object getBody() {
		return this.body;
	}
}

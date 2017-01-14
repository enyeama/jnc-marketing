package com.sap.jnc.marketing.service.exception.migration;

import com.sap.jnc.marketing.dto.response.FieldErrorResponse;

public class ResponseBodyDBException extends RuntimeException {

	private static final long serialVersionUID = 8246739510366781467L;
	private FieldErrorResponse errors;
	public ResponseBodyDBException(Exception ex, FieldErrorResponse errors) {
		super(ex);
		this.errors = errors;
	}
	public FieldErrorResponse getFieldErorrBody(){
		if (errors != null)
			return errors;
		else
			return null;
	}
}

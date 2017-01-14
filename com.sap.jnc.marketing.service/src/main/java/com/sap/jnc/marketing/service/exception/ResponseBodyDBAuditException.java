package com.sap.jnc.marketing.service.exception;

public class ResponseBodyDBAuditException extends RuntimeException {

	private static final long serialVersionUID = 8246739510366781467L;
	private FieldErrorBodyAudit errors;

	public ResponseBodyDBAuditException(Exception ex, FieldErrorBodyAudit errors) {
		super(ex);
		this.errors = errors;
	}

	public FieldErrorBodyAudit getFieldErorrBody() {
		if (errors != null)
			return errors;
		else
			return null;
	}
}

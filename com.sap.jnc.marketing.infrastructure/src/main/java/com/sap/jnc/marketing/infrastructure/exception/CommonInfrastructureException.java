package com.sap.jnc.marketing.infrastructure.exception;

import org.apache.commons.lang3.StringUtils;

public class CommonInfrastructureException extends RuntimeException {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -1430079449897854066L;

	protected String errorMessage;

	public CommonInfrastructureException(String errorMessage) {
		this.setErrorMessage(errorMessage);
	}

	public CommonInfrastructureException(String errorMessage, Exception ex) {
		super(ex);
		this.setErrorMessage(errorMessage);
	}

	public CommonInfrastructureException(Exception ex) {
		super(ex);
	}

	public CommonInfrastructureException(Throwable thr) {
		super(thr);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String getMessage() {
		if (StringUtils.isBlank(this.errorMessage)) {
			return super.getMessage();
		}
		else {
			return this.errorMessage;
		}
	}

	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

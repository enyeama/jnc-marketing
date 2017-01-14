package com.sap.jnc.marketing.service.exception;

import org.apache.commons.lang3.StringUtils;

public class CommonServiceException extends RuntimeException {

	private static final long serialVersionUID = -6803128408360342313L;

	protected String errorMessage;

	public CommonServiceException(String errorMessage) {
		this.setErrorMessage(errorMessage);
	}

	public CommonServiceException(String errorMessage, Exception ex) {
		super(ex);
		this.setErrorMessage(errorMessage);
	}

	public CommonServiceException(Exception ex) {
		super(ex);
	}

	public CommonServiceException(Throwable thr) {
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

package com.sap.jnc.marketing.service.exception.migration;

import org.apache.commons.lang3.StringUtils;

/**
 * @author I323691 Marco Huang 
 */

public class UploadFileInvalidFormatException extends RuntimeException  {
	
	/**
	 * Generate serial version id
	 */
	private static final long serialVersionUID = 3696658563796318964L;
	
	private String errorMessage;

	public UploadFileInvalidFormatException(String errorMessage) {
		this.setErrorMessage(errorMessage);
	}

	public UploadFileInvalidFormatException(String errorMessage, Exception ex) {
		super(ex);
		this.setErrorMessage(errorMessage);
	}

	public UploadFileInvalidFormatException(Exception ex) {
		super(ex);
	}

	public UploadFileInvalidFormatException(Throwable thr) {
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

package com.sap.jnc.marketing.service.exception.migration;

import org.apache.commons.lang3.StringUtils;

/**
 * @author I323691 Marco Huang 
 */

public class ExportExcelException extends RuntimeException {
	
	/**
	 * Generate serial version id
	 */
	private static final long serialVersionUID = -6183470653488030786L;
	
	protected String errorMessage;

	public ExportExcelException(String errorMessage) {
		this.setErrorMessage(errorMessage);
	}

	public ExportExcelException(String errorMessage, Exception ex) {
		super(ex);
		this.setErrorMessage(errorMessage);
	}

	public ExportExcelException(Exception ex) {
		super(ex);
	}

	public ExportExcelException(Throwable thr) {
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

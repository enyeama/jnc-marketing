package com.sap.jnc.marketing.api.admin.web.exception.contract;

public class ContractException extends RuntimeException {
	private static final long serialVersionUID = 1690031815931868159L;

	public ContractException(String error) {
		this.error = error;
	}

	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}

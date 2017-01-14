package com.sap.jnc.marketing.api.wechat.web.exception;

public class CommonWebException extends RuntimeException {

	private static final long serialVersionUID = -49093352328654622L;

	public CommonWebException(String errorMessage) {
		super(errorMessage);
	}

	public CommonWebException(String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}

	public CommonWebException(Exception ex) {
		super(ex);
	}
}

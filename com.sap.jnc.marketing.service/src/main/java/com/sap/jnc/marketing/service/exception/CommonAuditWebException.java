package com.sap.jnc.marketing.service.exception;

/**
 * @author C5231393 Xu Xiaolei
 */
public class CommonAuditWebException extends RuntimeException {

	private static final long serialVersionUID = -49093352328654622L;

	public CommonAuditWebException(String errorMessage) {
		super(errorMessage);
	}

	public CommonAuditWebException(String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}

	public CommonAuditWebException(Exception ex) {
		super(ex);
	}
}

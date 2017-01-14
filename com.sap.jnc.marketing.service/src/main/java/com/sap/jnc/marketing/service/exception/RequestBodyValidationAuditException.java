/*
 * This Exception is used to handle the process DB exception of the restful api call from JNC outbound logistic or JF production 
 * @author      James Jiang
 */
package com.sap.jnc.marketing.service.exception;

import org.springframework.validation.BindingResult;

public class RequestBodyValidationAuditException extends CommonAuditWebException {

	private static final long serialVersionUID = 8617177844267794294L;

	protected static final String DEFAULT_DESCRIPTION = "There are some invalid values in request body.";

	protected BindingResult bindingResult;

	public RequestBodyValidationAuditException(BindingResult bindingResult) {
		super(DEFAULT_DESCRIPTION);
		this.bindingResult = bindingResult;
	}

	public RequestBodyValidationAuditException(String errorMessage) {
		super(errorMessage);
	}

	public RequestBodyValidationAuditException(String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}

	public RequestBodyValidationAuditException(Exception ex) {
		super(ex);
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}
}

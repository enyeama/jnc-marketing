/*
 * This Exception is used to handle the process Field exception of the restful api call from JNC outbound logistic or JF production 
 * @author      James Jiang
 */
package com.sap.jnc.marketing.service.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class RequestBodyFieldValidationAuditException extends CommonAuditWebException {

	private static final long serialVersionUID = 7872794370661892795L;

	protected static final String DEFAULT_DESCRIPTION = "There are some invalid values in request body.";

	protected BindingResult bindingResult;

	public RequestBodyFieldValidationAuditException(BindingResult bindingResult) {
		super(DEFAULT_DESCRIPTION);
		this.bindingResult = bindingResult;
	}

	public RequestBodyFieldValidationAuditException(String errorMessage) {
		super(errorMessage);
	}

	public RequestBodyFieldValidationAuditException(String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}

	public RequestBodyFieldValidationAuditException(Exception ex) {
		super(ex);
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}
	
	public FieldErrorBodyAudit getFieldErorrBody() {
		if (bindingResult != null){
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			FieldErrorBodyAudit fieldErrorBodies = new FieldErrorBodyAudit();
			List<FieldErrorBodyAudit.FieldErrorBodyItems> itemList = new ArrayList<FieldErrorBodyAudit.FieldErrorBodyItems>();
        for (FieldError fieldError : fieldErrors) {
        		itemList.add(fieldErrorBodies.new FieldErrorBodyItems(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        }
        fieldErrorBodies.setError(itemList);
        	return fieldErrorBodies;
		}else{
			return null;
		}
	}
}
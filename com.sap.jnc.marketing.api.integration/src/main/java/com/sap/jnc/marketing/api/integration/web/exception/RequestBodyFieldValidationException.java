/*
 * This Exception is used to handle the process Field exception of the restful api call from JNC outbound logistic or JF production
 * @author James Jiang
 */
package com.sap.jnc.marketing.api.integration.web.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.sap.jnc.marketing.dto.response.FieldErrorResponse;

public class RequestBodyFieldValidationException extends CommonWebException {

	private static final long serialVersionUID = 7872794370661892795L;

	protected static final String DEFAULT_DESCRIPTION = "There are some invalid values in request body.";

	protected BindingResult bindingResult;

	public RequestBodyFieldValidationException(BindingResult bindingResult) {
		super(DEFAULT_DESCRIPTION);
		this.bindingResult = bindingResult;
	}

	public RequestBodyFieldValidationException(String errorMessage) {
		super(errorMessage);
	}

	public RequestBodyFieldValidationException(String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}

	public RequestBodyFieldValidationException(Exception ex) {
		super(ex);
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public FieldErrorResponse getFieldErorrBody() {
		if (bindingResult != null) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			FieldErrorResponse fieldErrorResponses = new FieldErrorResponse();
			List<FieldErrorResponse.FieldErrorBodyItems> itemList = new ArrayList<FieldErrorResponse.FieldErrorBodyItems>();
			for (FieldError fieldError : fieldErrors) {
				String field = fieldError.getField();
				int itemId = 0;
				if (field != null && field.length() > 6) {
					itemId = Integer.parseInt(String.valueOf(field.charAt(6)));
				}
				String outputMessage = fieldError.getDefaultMessage();
				if (fieldError.getDefaultMessage() != null) {
					if (!"瓶内码已存在".equals(fieldError.getDefaultMessage()) && !"瓶外码已存在".equals(fieldError.getDefaultMessage())) {
						outputMessage = "100" + outputMessage;
					}
				}

				itemList.add(fieldErrorResponses.new FieldErrorBodyItems(fieldError.getField(), fieldError.getRejectedValue(), outputMessage,
					itemId));

			}
			fieldErrorResponses.setError(itemList);
			return fieldErrorResponses;
		}
		else {
			return null;
		}
	}
}
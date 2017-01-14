/*
 * This Exception is used to handle the process Field exception of the restful api call from JNC outbound logistic or JF production
 * @author James Jiang
 */
package com.sap.jnc.marketing.service.exception.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.sap.jnc.marketing.dto.response.FieldErrorResponse;
import com.sap.jnc.marketing.dto.response.ObjectErrorResponse;
import com.sap.jnc.marketing.service.exception.CommonWebException;

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
		return this.bindingResult;
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public FieldErrorResponse getFieldErorrBody() {
		if (this.bindingResult != null) {
			final List<FieldError> fieldErrors = this.bindingResult.getFieldErrors();
			final FieldErrorResponse fieldErrorResponses = new FieldErrorResponse();
			final List<FieldErrorResponse.FieldErrorBodyItems> itemList = new ArrayList<FieldErrorResponse.FieldErrorBodyItems>();
			for (final FieldError fieldError : fieldErrors) {
				itemList.add(fieldErrorResponses.new FieldErrorBodyItems(fieldError.getField(), fieldError.getRejectedValue(), fieldError
					.getDefaultMessage(), 1));
			}
			fieldErrorResponses.setError(itemList);
			return fieldErrorResponses;
		}
		else {
			return null;
		}
	}

	public ObjectErrorResponse getObjectErrorResponse() {
		if ((this.bindingResult == null) || !this.bindingResult.hasErrors()) {
			return null;
		}
		return new ObjectErrorResponse(this.bindingResult.getAllErrors().stream()
			// Map
			.map(e -> new ObjectErrorResponse.Item(e.getCode(), e.getObjectName(), e.getDefaultMessage()))
			// Collect
			.collect(Collectors.toList()));
	}
}
package com.sap.jnc.marketing.api.integration.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import com.sap.jnc.marketing.api.integration.web.exception.RequestBodyValidationException;

/**
 * @author I071053 Diouf Du
 */
public abstract class GeneralController {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private HttpServletResponse httpServletResponse;

	protected HttpServletRequest getHttpServletRequest() {
		return this.httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	protected HttpServletResponse getHttpServletResponse() {
		return this.httpServletResponse;
	}

	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}

	protected void validateBindingResult(BindingResult bindingResult) {
		if ((bindingResult != null) && bindingResult.hasErrors()) {
			throw new RequestBodyValidationException(bindingResult);
		}
	}
}

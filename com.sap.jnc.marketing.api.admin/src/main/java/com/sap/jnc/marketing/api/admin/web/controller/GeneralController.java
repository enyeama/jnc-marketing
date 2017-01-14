package com.sap.jnc.marketing.api.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import com.sap.jnc.marketing.api.admin.web.exception.RequestBodyValidationException;

/**
 * @author I071053 Diouf Du
 */
public abstract class GeneralController {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private HttpServletResponse httpServletResponse;

	protected HttpSession getHttpSession() {
		return this.httpSession;
	}

	protected void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	protected HttpServletRequest getHttpServletRequest() {
		return this.httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
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

/*
 * This controller is used to receive the restful api call from JF production
 * @author James Jiang
 */
package com.sap.jnc.marketing.api.integration.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.api.integration.web.exception.AuthorizationException;
import com.sap.jnc.marketing.api.integration.web.exception.RequestBodyFieldValidationException;
import com.sap.jnc.marketing.api.integration.web.interceptor.JfAccessRequired;
import com.sap.jnc.marketing.dto.request.integration.JFDeleteRequest;
import com.sap.jnc.marketing.dto.request.integration.JFMergeRequest;
import com.sap.jnc.marketing.dto.request.integration.JFUpdateRequest;
import com.sap.jnc.marketing.dto.response.FieldErrorResponse;
import com.sap.jnc.marketing.dto.validator.integration.JFCreateUpdateGroup;
import com.sap.jnc.marketing.dto.validator.integration.JFDeleteGroup;
import com.sap.jnc.marketing.service.authorization.IntegrationAuthorizationService;
import com.sap.jnc.marketing.service.exception.migration.ResponseBodyDBException;
import com.sap.jnc.marketing.service.integration.jf.JFIntegrationService;

import me.chanjar.weixin.common.util.StringUtils;

@RestController
public class JFIntegrationController extends GeneralController {
	@Autowired
	private JFIntegrationService jFIntegrationService;

	@Autowired
	private IntegrationAuthorizationService integrationAuthrizationService;

	@RequestMapping(path = "/jfproduction", method = { RequestMethod.POST })
	@JfAccessRequired
	public JFMergeRequest create(@RequestBody @Validated({ JFCreateUpdateGroup.class }) JFMergeRequest dto, BindingResult result) {
		if (result.hasErrors()) {
			throw new RequestBodyFieldValidationException(result);
		}
		return jFIntegrationService.create(dto);
	}

	@RequestMapping(path = "/jfproduction", method = { RequestMethod.PUT })
	@JfAccessRequired
	public JFUpdateRequest update(@RequestBody @Validated({ JFCreateUpdateGroup.class }) JFUpdateRequest dto, BindingResult result) {
		if (result.hasErrors()) {
			throw new RequestBodyFieldValidationException(result);
		}
		return jFIntegrationService.update(dto);
	}

	@RequestMapping(path = "/jfproduction", method = { RequestMethod.DELETE })
	@JfAccessRequired
	public JFDeleteRequest delete(@RequestBody @Validated({ JFDeleteGroup.class }) JFDeleteRequest dto, BindingResult result) {
		if (result.hasErrors()) {
			throw new RequestBodyFieldValidationException(result);
		}
		return jFIntegrationService.delete(dto);

	}

	private boolean auth() {
		boolean returnValue = false;
		HttpServletRequest request = getHttpServletRequest();
		String authCode = (String) request.getHeader("Authorization");
		if (StringUtils.isNotBlank(authCode)) {
			returnValue = integrationAuthrizationService.authorize(authCode);
		}
		if (returnValue == false) {
			throw new AuthorizationException("Authorization Error");
		}
		return returnValue;
	}

	@ExceptionHandler(RequestBodyFieldValidationException.class)
	public ResponseEntity<FieldErrorResponse> rulesForCustomerNotFound(RequestBodyFieldValidationException e) {
		return new ResponseEntity<FieldErrorResponse>(e.getFieldErorrBody(), HttpStatus.SEE_OTHER);

	}

	@ExceptionHandler(ResponseBodyDBException.class)
	public ResponseEntity<FieldErrorResponse> dbConnectionErrorHandler(ResponseBodyDBException e) {
		return new ResponseEntity<FieldErrorResponse>(e.getFieldErorrBody(), HttpStatus.SEE_OTHER);

	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<String> authorizationErrorHandler(AuthorizationException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);

	}

}

/*
 * This controller is used to receive the restful api call from JNC outbound logistic
 * @author James Jiang
 */
package com.sap.jnc.marketing.api.integration.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.api.integration.web.exception.RequestBodyFieldValidationException;
import com.sap.jnc.marketing.dto.request.integration.JNCOuntboundInfo;
import com.sap.jnc.marketing.dto.response.FieldErrorResponse;
import com.sap.jnc.marketing.service.exception.migration.ResponseBodyDBException;
import com.sap.jnc.marketing.service.outbound.JNCOutboundService;

@RestController
public class JNCOutboundController extends GeneralController {
	@Autowired
	private JNCOutboundService jNCOutboundService;

	@RequestMapping(path = "/outbound", method = { RequestMethod.POST })
	public JNCOuntboundInfo handlRequest(@RequestBody @Valid JNCOuntboundInfo outboundInfo, BindingResult result) {
		if (result.hasErrors()) {
			throw new RequestBodyFieldValidationException(result);
		}
		jNCOutboundService.dispatchOutboundInfo(outboundInfo);
		return outboundInfo;
	}

	/*
	 * This method will handle all the exception of field validation error include NotBland error, format error, instance consistency check error.
	 */
	@ExceptionHandler(RequestBodyFieldValidationException.class)
	public ResponseEntity<FieldErrorResponse> rulesForCustomerNotFound(RequestBodyFieldValidationException e) {
		return new ResponseEntity<FieldErrorResponse>(e.getFieldErorrBody(), HttpStatus.FORBIDDEN);

	}

	/*
	 * This method will handle all the exception of DB operation error include unkown error.
	 */
	@ExceptionHandler(ResponseBodyDBException.class)
	public ResponseEntity<FieldErrorResponse> dbConnectionErrorHandler(ResponseBodyDBException e) {
		return new ResponseEntity<FieldErrorResponse>(e.getFieldErorrBody(), HttpStatus.FORBIDDEN);

	}
}

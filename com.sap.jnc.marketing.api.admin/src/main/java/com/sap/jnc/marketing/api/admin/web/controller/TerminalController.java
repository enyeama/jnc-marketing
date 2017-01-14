package com.sap.jnc.marketing.api.admin.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.logistic.LogisticRequest;
import com.sap.jnc.marketing.service.logistic.LogisticService;

/**
 * @author I071053 Diouf Du
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {

	private static Logger LOGGER = LoggerFactory.getLogger(TerminalController.class);

	@Autowired
	private LogisticService logisticService;

}

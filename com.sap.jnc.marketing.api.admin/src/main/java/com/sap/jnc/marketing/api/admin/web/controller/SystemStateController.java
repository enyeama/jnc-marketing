package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Calendar;

import javax.annotation.security.PermitAll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author I071053 Diouf Du
 */
@RestController
@RequestMapping("/system")
public class SystemStateController extends GeneralController {

    private static Logger LOGGER = LoggerFactory.getLogger(SystemStateController.class);

	@PermitAll
	@RequestMapping("/state")
	public String checkState() {
		LOGGER.info("check server state at {}", Calendar.getInstance());
		return "Working";
	}

}

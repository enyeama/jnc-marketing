package com.sap.jnc.marketing.api.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.service.user.UserMigrationService;

/**
 * @author I071053 Diouf Du
 */
@RestController
public class UserController extends GeneralController {

	@Autowired
	private UserMigrationService<Employee> userEmployeeMigrationService;

	@Autowired
	private UserMigrationService<Dealer> userDealerMigrationService;

	@Autowired
	private UserMigrationService<Terminal> userTerminalMigrationService;

	@RequestMapping(value = "/employees/users", method = RequestMethod.GET)
	public void createUsersForEmployee() {
		this.userEmployeeMigrationService.createUsers();
	}

	@RequestMapping(value = "/dealers/users", method = RequestMethod.GET)
	public void createUsersForDealers() {
		this.userDealerMigrationService.createUsers();
	}

	@RequestMapping(value = "/terminals/users", method = RequestMethod.GET)
	public void createUsersForTerminal() {
		this.userTerminalMigrationService.createUsers();
	}
}

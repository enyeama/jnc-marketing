package com.sap.jnc.marketing.service.user.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.MD5;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.LoginAccount;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.model.UserReferenceToEmployee;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.RoleRepository;
import com.sap.jnc.marketing.persistence.repository.UserReferenceRepository;
import com.sap.jnc.marketing.persistence.repository.UserRepository;
import com.sap.jnc.marketing.service.user.UserMigrationService;

@Service
@Transactional
public class EmployeeMigrationUserServiceImpl implements UserMigrationService<Employee> {

	private static final int DEFAULT_BATCH_PAGE_SIZE = 500;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserReferenceRepository userReferenceRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Override
	@Transactional
	public void createUsers() {
		// Search for those employee don't have user references
		int batchIndex = 0;
		Page<EmployeeView> employeesWithNoUserReference = null;
		do {
			employeesWithNoUserReference = this.employeeViewRepository.findAllWithNoUserReference(
				// Handle by batch
				new PageRequest(batchIndex++, DEFAULT_BATCH_PAGE_SIZE));
			this.createUserAndReferenceForEmployee(employeesWithNoUserReference.getContent());
		}
		while ((employeesWithNoUserReference != null) && !employeesWithNoUserReference.isLast());
	}

	private void createUserAndReferenceForEmployee(final Collection<EmployeeView> employeesWithNoUserReference) {
		for (final EmployeeView employeeWithNoUserReference : employeesWithNoUserReference) {
			// Create user
			User user = new User();
			user.setName(employeeWithNoUserReference.getName());
			user.setLoginAccount(new LoginAccount());
			user.getLoginAccount().setMobile(employeeWithNoUserReference.getPhone());
			user = this.userRepository.save(user);
			// Set user name and password
			user.getLoginAccount().setUserName(String.valueOf(100000 + user.getId()));
			if (StringUtils.isNotBlank(user.getLoginAccount().getUserName())) {
				user.getLoginAccount().setPassword(MD5.MD5Encode(user.getLoginAccount().getUserName()));
			}
			else {
				user.getLoginAccount().setPassword(MD5.MD5Encode(employeeWithNoUserReference.getExternalId()));
			}
			// Assign roles
			Role role = this.findRole(employeeWithNoUserReference.getLoginAccountRole());
			user.setRoles(new ArrayList<>(1));
			user.getRoles().add(role);
			if (CollectionUtils.isEmpty(role.getUsers())) {
				role.setUsers(new ArrayList<>(1));
			}
			role.getUsers().add(user);
			role = this.roleRepository.save(role);
			// Create user reference and assign relationships
			UserReferenceToEmployee userReferenceToEmployee = new UserReferenceToEmployee();
			userReferenceToEmployee.setEmployee(employeeWithNoUserReference);
			userReferenceToEmployee.setUsers(new ArrayList<>(1));
			userReferenceToEmployee.getUsers().add(user);
			// Save user reference
			userReferenceToEmployee = this.userReferenceRepository.save(userReferenceToEmployee);
			// Save user
			user = this.userRepository.save(user);
		}
		this.userRepository.flush();
	}

	@Cacheable
	private Role findRole(String roleName) {
		return this.roleRepository.findOneByName(roleName);
	}
}

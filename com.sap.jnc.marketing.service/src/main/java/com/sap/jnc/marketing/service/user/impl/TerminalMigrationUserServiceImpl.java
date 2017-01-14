package com.sap.jnc.marketing.service.user.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.MD5;
import com.sap.jnc.marketing.persistence.model.LoginAccount;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.model.UserReferenceToTerminal;
import com.sap.jnc.marketing.persistence.model.UserVerificationForTerminal;
import com.sap.jnc.marketing.persistence.repository.RoleRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.persistence.repository.UserReferenceRepository;
import com.sap.jnc.marketing.persistence.repository.UserRepository;
import com.sap.jnc.marketing.service.user.UserMigrationService;

@Service
@Transactional
public class TerminalMigrationUserServiceImpl implements UserMigrationService<Terminal> {

	private static final int DEFAULT_BATCH_PAGE_SIZE = 500;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserReferenceRepository userReferenceRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TerminalRepository terminalRepository;

	private Role terminalRole;

	@Override
	@Transactional
	public void createUsers() {
		// Search for those terminal don't have user references
		int batchIndex = 0;
		Page<Terminal> terminalsWithNoUserReference = null;
		do {
			terminalsWithNoUserReference = this.terminalRepository.findAllWithNoUserReference(
				// Handle by batch
				new PageRequest(batchIndex++, DEFAULT_BATCH_PAGE_SIZE));
			this.createUserAndReferenceForTerminal(terminalsWithNoUserReference.getContent());
		}
		while ((terminalsWithNoUserReference != null) && !terminalsWithNoUserReference.isLast());
	}

	private void createUserAndReferenceForTerminal(final Collection<Terminal> terminalsWithNoUserReference) {
		for (final Terminal terminalWithNoUserReference : terminalsWithNoUserReference) {
			this.createUserAndReferenceForTerminal(terminalWithNoUserReference);
		}
		this.userRepository.flush();
	}

	private void createUserAndReferenceForTerminal(final Terminal terminalWithNoUserReference) {
		for (final UserVerificationForTerminal userVerificationForTerminal : terminalWithNoUserReference.getUserVerifications()) {
			// Create user
			User user = new User();
			user.setName(userVerificationForTerminal.getName());
			user.setLoginAccount(new LoginAccount());
			user.getLoginAccount().setMobile(userVerificationForTerminal.getMobile());
			user = this.userRepository.save(user);
			// Set user name and password
			user.getLoginAccount().setUserName(String.valueOf(100000 + user.getId()));
			if (StringUtils.isNotBlank(user.getLoginAccount().getUserName())) {
				user.getLoginAccount().setPassword(MD5.MD5Encode(user.getLoginAccount().getUserName()));
			}
			else {
				user.getLoginAccount().setPassword(MD5.MD5Encode(userVerificationForTerminal.getExternalId()));
			}
			// Assign roles
			Role terminalRole = this.findTerminalRole();
			user.setRoles(new ArrayList<>(1));
			user.getRoles().add(terminalRole);
			if (CollectionUtils.isEmpty(terminalRole.getUsers())) {
				terminalRole.setUsers(new ArrayList<>(1));
			}
			terminalRole.getUsers().add(user);
			terminalRole = this.roleRepository.save(terminalRole);
			// Save user
			user = this.userRepository.save(user);
			// Create user reference and assign relationships
			UserReferenceToTerminal userReferenceToTerminal = new UserReferenceToTerminal();
			userReferenceToTerminal.setTerminal(terminalWithNoUserReference);
			userReferenceToTerminal.setUsers(new ArrayList<>(1));
			userReferenceToTerminal.getUsers().add(user);
			// Save user reference
			userReferenceToTerminal = this.userReferenceRepository.save(userReferenceToTerminal);
		}
	}

	private Role findTerminalRole() {
		if (this.terminalRole == null) {
			this.terminalRole = this.roleRepository.findOneByName("终端");
		}
		return this.terminalRole;
	}
}

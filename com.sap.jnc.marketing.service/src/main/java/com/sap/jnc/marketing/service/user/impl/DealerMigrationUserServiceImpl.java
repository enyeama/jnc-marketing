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
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.LoginAccount;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.model.UserReferenceToDealer;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.RoleRepository;
import com.sap.jnc.marketing.persistence.repository.UserReferenceRepository;
import com.sap.jnc.marketing.persistence.repository.UserRepository;
import com.sap.jnc.marketing.service.user.UserMigrationService;

@Service
@Transactional
public class DealerMigrationUserServiceImpl implements UserMigrationService<Dealer> {

	private static final int DEFAULT_BATCH_PAGE_SIZE = 500;

	private static final String[] ACCOUNT_SERIES = new String[] { "A", "B", "C", "D", "E" };

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserReferenceRepository userReferenceRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private DealerRepository dealerRepository;

	private Role dealerRole;

	@Override
	@Transactional
	public void createUsers() {
		// Search for those dealer don't have user references
		int batchIndex = 0;
		Page<Dealer> dealersWithNoUserReference = null;
		do {
			dealersWithNoUserReference = this.dealerRepository.findAllWithNoUserReference(
				// Handle by batch
				new PageRequest(batchIndex++, DEFAULT_BATCH_PAGE_SIZE));
			this.createUserAndReferenceForDealer(dealersWithNoUserReference.getContent());
		}
		while ((dealersWithNoUserReference != null) && !dealersWithNoUserReference.isLast());
	}

	private void createUserAndReferenceForDealer(final Collection<Dealer> dealersWithNoUserReference) {
		for (final Dealer dealerWithNoUserReference : dealersWithNoUserReference) {
			this.createUserAndReferenceForDealer(dealerWithNoUserReference);
		}
		this.userRepository.flush();
	}

	private void createUserAndReferenceForDealer(final Dealer dealerWithNoUserReference) {
		for (final String accountSerie : ACCOUNT_SERIES) {
			// Create user
			User user = new User();
			user.setName(dealerWithNoUserReference.getName() + accountSerie);
			user.setLoginAccount(new LoginAccount());
			user = this.userRepository.save(user);
			// Set user name and password
			user.getLoginAccount().setUserName(String.valueOf(100000 + user.getId()));
			if (StringUtils.isNotBlank(user.getLoginAccount().getUserName())) {
				user.getLoginAccount().setPassword(MD5.MD5Encode(user.getLoginAccount().getUserName()));
			}
			else {
				user.getLoginAccount().setPassword(MD5.MD5Encode(dealerWithNoUserReference.getExternalId()));
			}
			// Assign roles
			Role dealerRole = this.findDealerRole();
			user.setRoles(new ArrayList<>(1));
			user.getRoles().add(dealerRole);
			if (CollectionUtils.isEmpty(dealerRole.getUsers())) {
				dealerRole.setUsers(new ArrayList<>(1));
			}
			dealerRole = this.roleRepository.save(dealerRole);
			dealerRole.getUsers().add(user);
			// Create user reference and assign relationships
			UserReferenceToDealer userReferenceToDealer = new UserReferenceToDealer();
			userReferenceToDealer.setDealer(dealerWithNoUserReference);
			userReferenceToDealer.setUsers(new ArrayList<>(1));
			userReferenceToDealer.getUsers().add(user);
			// Save
			userReferenceToDealer = this.userReferenceRepository.save(userReferenceToDealer);
			// Save user
			user = this.userRepository.save(user);
		}
	}

	private Role findDealerRole() {
		if (this.dealerRole == null) {
			this.dealerRole = this.roleRepository.findOneByName("经销商");
		}
		return this.dealerRole;
	}
}

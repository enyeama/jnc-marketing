package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.UserReferenceToTerminal;
import com.sap.jnc.marketing.persistence.repository.UserReferenceToTerminalRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserReferenceToTerminalImpl extends SimpleJpaRepository<UserReferenceToTerminal, Long> implements UserReferenceToTerminalRepository {

	@Autowired
	public UserReferenceToTerminalImpl(EntityManager em) {
		super(UserReferenceToTerminal.class, em);
	}
}

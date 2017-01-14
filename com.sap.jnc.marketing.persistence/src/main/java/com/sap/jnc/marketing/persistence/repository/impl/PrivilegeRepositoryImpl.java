package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.Privilege;
import com.sap.jnc.marketing.persistence.model.Privilege_;
import com.sap.jnc.marketing.persistence.repository.PrivilegeRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class PrivilegeRepositoryImpl extends GeneralJpaRepositoryImpl<Privilege, Privilege_, Long> implements PrivilegeRepository {

	@Autowired
	public PrivilegeRepositoryImpl(EntityManager em) {
		super(Privilege.class, em);
	}
}

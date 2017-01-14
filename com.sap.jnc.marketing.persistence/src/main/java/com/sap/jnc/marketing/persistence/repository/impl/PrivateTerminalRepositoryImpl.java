package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.IntegratingMarketing;
import com.sap.jnc.marketing.persistence.repository.PrivateTerminalRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrivateTerminalRepositoryImpl extends SimpleJpaRepository<IntegratingMarketing, String> implements PrivateTerminalRepository {

	@Autowired
	public PrivateTerminalRepositoryImpl(EntityManager em) {
		super(IntegratingMarketing.class, em);
	}
}

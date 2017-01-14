package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.TerminalLevel;
import com.sap.jnc.marketing.persistence.repository.TerminalLevelRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TerminalLevelRepositoryImpl extends SimpleJpaRepository<TerminalLevel, Long> implements TerminalLevelRepository {

	@Autowired
	public TerminalLevelRepositoryImpl(EntityManager em) {
		super(TerminalLevel.class, em);
	}
}

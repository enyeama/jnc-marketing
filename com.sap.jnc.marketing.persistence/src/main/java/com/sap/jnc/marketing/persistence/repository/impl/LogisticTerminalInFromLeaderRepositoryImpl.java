package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.LogisticTerminalInFromLeader;
import com.sap.jnc.marketing.persistence.repository.LogisticTerminalInFromLeaderRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogisticTerminalInFromLeaderRepositoryImpl extends SimpleJpaRepository<LogisticTerminalInFromLeader, Long> implements LogisticTerminalInFromLeaderRepository {

	@Autowired
	public LogisticTerminalInFromLeaderRepositoryImpl(EntityManager em) {
		super(LogisticTerminalInFromLeader.class, em);
	}
}

package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.LogisticTerminalInFromDealer;
import com.sap.jnc.marketing.persistence.repository.LogisticTerminalInFromDealerRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogisticTerminalInFromDealerRepositoryImpl extends SimpleJpaRepository<LogisticTerminalInFromDealer, Long> implements LogisticTerminalInFromDealerRepository {

	@Autowired
	public LogisticTerminalInFromDealerRepositoryImpl(EntityManager em) {
		super(LogisticTerminalInFromDealer.class, em);
	}
}

package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.LogisticDealerOutToTerminal;
import com.sap.jnc.marketing.persistence.repository.LogisticDealerOutToTerminalRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogisticDealerOutToTerminalRepositoryImpl extends SimpleJpaRepository<LogisticDealerOutToTerminal, Long> implements LogisticDealerOutToTerminalRepository {

	@Autowired
	public LogisticDealerOutToTerminalRepositoryImpl(EntityManager em) {
		super(LogisticDealerOutToTerminal.class, em);
	}
}

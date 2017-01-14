package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.LogisticDealerIn;
import com.sap.jnc.marketing.persistence.repository.LogisticDealerInRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogisticDealerInRepositoryImpl extends SimpleJpaRepository<LogisticDealerIn, Long> implements LogisticDealerInRepository {

	@Autowired
	public LogisticDealerInRepositoryImpl(EntityManager em) {
		super(LogisticDealerIn.class, em);
	}
}

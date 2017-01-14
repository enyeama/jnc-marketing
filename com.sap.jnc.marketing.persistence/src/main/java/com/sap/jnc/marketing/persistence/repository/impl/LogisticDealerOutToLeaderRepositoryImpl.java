package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.LogisticDealerOutToLeader;
import com.sap.jnc.marketing.persistence.repository.LogisticDealerOutToLeaderRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogisticDealerOutToLeaderRepositoryImpl extends SimpleJpaRepository<LogisticDealerOutToLeader, Long> implements LogisticDealerOutToLeaderRepository {

	@Autowired
	public LogisticDealerOutToLeaderRepositoryImpl(EntityManager em) {
		super(LogisticDealerOutToLeader.class, em);
	}
}

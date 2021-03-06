package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.LogisticLeaderIn;
import com.sap.jnc.marketing.persistence.repository.LogisticLeaderInRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogisticLeaderInRepositoryImpl extends SimpleJpaRepository<LogisticLeaderIn, Long> implements LogisticLeaderInRepository {

	@Autowired
	public LogisticLeaderInRepositoryImpl(EntityManager em) {
		super(LogisticLeaderIn.class, em);
	}
}

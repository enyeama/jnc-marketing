package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.LogisticLeaderOut;
import com.sap.jnc.marketing.persistence.repository.LogisticLeaderOutRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogisticLeaderOutRepositoryImpl extends SimpleJpaRepository<LogisticLeaderOut, Long> implements LogisticLeaderOutRepository {

	@Autowired
	public LogisticLeaderOutRepositoryImpl(EntityManager em) {
		super(LogisticLeaderOut.class, em);
	}
}

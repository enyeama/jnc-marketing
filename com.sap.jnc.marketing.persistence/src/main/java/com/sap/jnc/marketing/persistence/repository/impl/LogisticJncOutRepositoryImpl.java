package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.LogisticJncOut;
import com.sap.jnc.marketing.persistence.repository.LogisticJncOutRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogisticJncOutRepositoryImpl extends SimpleJpaRepository<LogisticJncOut, Long> implements LogisticJncOutRepository {

	@Autowired
	public LogisticJncOutRepositoryImpl(EntityManager em) {
		super(LogisticJncOut.class, em);
	}
}

package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.IntegratingMarketing;
import com.sap.jnc.marketing.persistence.repository.IntegratingMarketingRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IntegratingMarketingRepositoryImpl extends SimpleJpaRepository<IntegratingMarketing, Long> implements IntegratingMarketingRepository {

	@Autowired
	public IntegratingMarketingRepositoryImpl(EntityManager em) {
		super(IntegratingMarketing.class, em);
	}
}

package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.IndividualProductRebate;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRebateRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)

public class IndividualProductRebateRepositoryImpl extends SimpleJpaRepository<IndividualProductRebate, Long> implements IndividualProductRebateRepository {

	@Autowired
	public IndividualProductRebateRepositoryImpl(EntityManager em) {
		super(IndividualProductRebate.class, em);
	}
}

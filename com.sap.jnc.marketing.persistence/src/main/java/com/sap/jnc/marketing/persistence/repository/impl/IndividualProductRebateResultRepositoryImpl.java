package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.IndividualProductRebateResult;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRebateResultRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)

public class IndividualProductRebateResultRepositoryImpl extends SimpleJpaRepository<IndividualProductRebateResult, String> implements IndividualProductRebateResultRepository {

	@Autowired
	public IndividualProductRebateResultRepositoryImpl(EntityManager em) {
		super(IndividualProductRebateResult.class, em);
	}
}

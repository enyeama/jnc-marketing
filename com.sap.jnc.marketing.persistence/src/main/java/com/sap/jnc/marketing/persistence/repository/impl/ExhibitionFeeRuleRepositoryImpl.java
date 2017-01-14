package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.ExhibitionFeeRule;
import com.sap.jnc.marketing.persistence.repository.ExhibitionFeeRuleRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExhibitionFeeRuleRepositoryImpl extends SimpleJpaRepository<ExhibitionFeeRule, Long> implements ExhibitionFeeRuleRepository {

	@Autowired
	public ExhibitionFeeRuleRepositoryImpl(EntityManager em) {
		super(ExhibitionFeeRule.class, em);
	}
}
package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.BanquetRebate;
import com.sap.jnc.marketing.persistence.repository.BanquetRebateRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BanquetRebateRepositoryImpl extends SimpleJpaRepository<BanquetRebate, Long> implements BanquetRebateRepository {

	@Autowired
	public BanquetRebateRepositoryImpl(EntityManager em) {
		super(BanquetRebate.class, em);
	}
}
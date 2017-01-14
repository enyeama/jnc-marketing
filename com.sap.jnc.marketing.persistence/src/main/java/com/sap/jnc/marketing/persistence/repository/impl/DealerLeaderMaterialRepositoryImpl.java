package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.repository.DealerLeaderMaterialRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DealerLeaderMaterialRepositoryImpl extends SimpleJpaRepository<Dealer, Long> implements DealerLeaderMaterialRepository {

	@Autowired
	public DealerLeaderMaterialRepositoryImpl(EntityManager em) {
		super(Dealer.class, em);
	}
}

package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.DealerOrder;
import com.sap.jnc.marketing.persistence.repository.DealerOrderRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DealerOrderRepositoryImpl extends SimpleJpaRepository<DealerOrder, Long> implements DealerOrderRepository {

	@Autowired
	public DealerOrderRepositoryImpl(EntityManager em) {
		super(DealerOrder.class, em);
	}
}

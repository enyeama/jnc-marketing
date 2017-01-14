package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.DealerOrderItem;
import com.sap.jnc.marketing.persistence.repository.DealerOrderItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DealerOrderItemRepositoryImpl extends SimpleJpaRepository<DealerOrderItem, Long> implements DealerOrderItemRepository {

	@Autowired
	public DealerOrderItemRepositoryImpl(EntityManager em) {
		super(DealerOrderItem.class, em);
	}
}

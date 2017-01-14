package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.DmsOrderItem;
import com.sap.jnc.marketing.persistence.repository.DmsOrderItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DmsOrderItemRepositoryImpl extends SimpleJpaRepository<DmsOrderItem, Long> implements DmsOrderItemRepository {

	@Autowired
	public DmsOrderItemRepositoryImpl(EntityManager em) {
		super(DmsOrderItem.class, em);
	}
}
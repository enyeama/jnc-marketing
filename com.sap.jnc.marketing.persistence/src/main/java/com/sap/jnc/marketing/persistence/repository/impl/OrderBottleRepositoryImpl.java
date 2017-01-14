package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.OrderBottle;
import com.sap.jnc.marketing.persistence.repository.OrderBottleRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderBottleRepositoryImpl extends SimpleJpaRepository<OrderBottle, Long> implements OrderBottleRepository {

	@Autowired
	public OrderBottleRepositoryImpl(EntityManager em) {
		super(OrderBottle.class, em);
	}
}

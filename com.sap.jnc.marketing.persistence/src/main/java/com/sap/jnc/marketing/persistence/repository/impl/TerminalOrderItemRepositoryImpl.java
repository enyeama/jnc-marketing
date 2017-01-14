package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.TerminalOrderItem;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TerminalOrderItemRepositoryImpl extends SimpleJpaRepository<TerminalOrderItem, Long> implements TerminalOrderItemRepository {

	@Autowired
	public TerminalOrderItemRepositoryImpl(EntityManager em) {
		super(TerminalOrderItem.class, em);
	}
}

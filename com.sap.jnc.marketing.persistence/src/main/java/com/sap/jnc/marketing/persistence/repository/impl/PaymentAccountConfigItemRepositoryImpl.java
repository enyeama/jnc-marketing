package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.PaymentAccountConfigItem;
import com.sap.jnc.marketing.persistence.repository.PaymentAccountConfigItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PaymentAccountConfigItemRepositoryImpl extends SimpleJpaRepository<PaymentAccountConfigItem, Long> implements PaymentAccountConfigItemRepository {

	@Autowired
	public PaymentAccountConfigItemRepositoryImpl(EntityManager em) {
		super(PaymentAccountConfigItem.class, em);
	}
}

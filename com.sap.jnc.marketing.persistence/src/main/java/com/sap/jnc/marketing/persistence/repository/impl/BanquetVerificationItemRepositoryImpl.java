package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.BanquetVerificationItem;
import com.sap.jnc.marketing.persistence.repository.BanquetVerificationItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BanquetVerificationItemRepositoryImpl extends SimpleJpaRepository<BanquetVerificationItem, Long> implements BanquetVerificationItemRepository {

	@Autowired
	public BanquetVerificationItemRepositoryImpl(EntityManager em) {
		super(BanquetVerificationItem.class, em);
	}
}

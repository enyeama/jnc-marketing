package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.BonusDispatchConfigItem;
import com.sap.jnc.marketing.persistence.repository.BonusDispatchConfigItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BonusDispatchConfigItemRepositoryImpl extends SimpleJpaRepository<BonusDispatchConfigItem, Long> implements BonusDispatchConfigItemRepository {

	@Autowired
	public BonusDispatchConfigItemRepositoryImpl(EntityManager em) {
		super(BonusDispatchConfigItem.class, em);
	}
}

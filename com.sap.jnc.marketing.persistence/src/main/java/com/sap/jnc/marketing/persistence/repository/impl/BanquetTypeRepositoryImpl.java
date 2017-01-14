package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.BanquetType;
import com.sap.jnc.marketing.persistence.repository.BanquetTypeRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BanquetTypeRepositoryImpl extends SimpleJpaRepository<BanquetType, Long> implements BanquetTypeRepository {

	@Autowired
	public BanquetTypeRepositoryImpl(EntityManager em) {
		super(BanquetType.class, em);
	}
}

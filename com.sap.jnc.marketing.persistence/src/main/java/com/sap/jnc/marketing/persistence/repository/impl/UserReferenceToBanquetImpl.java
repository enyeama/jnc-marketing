package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.UserReferenceToBanquet;
import com.sap.jnc.marketing.persistence.repository.UserReferenceToBanquetRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserReferenceToBanquetImpl extends SimpleJpaRepository<UserReferenceToBanquet, Long> implements UserReferenceToBanquetRepository {

	@Autowired
	public UserReferenceToBanquetImpl(EntityManager em) {
		super(UserReferenceToBanquet.class, em);
	}
}

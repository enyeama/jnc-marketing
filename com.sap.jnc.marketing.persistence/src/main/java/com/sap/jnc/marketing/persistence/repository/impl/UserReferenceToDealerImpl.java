package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.UserReferenceToDealer;
import com.sap.jnc.marketing.persistence.repository.UserReferenceToDealerRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserReferenceToDealerImpl extends SimpleJpaRepository<UserReferenceToDealer, Long> implements UserReferenceToDealerRepository {

	@Autowired
	public UserReferenceToDealerImpl(EntityManager em) {
		super(UserReferenceToDealer.class, em);
	}
}

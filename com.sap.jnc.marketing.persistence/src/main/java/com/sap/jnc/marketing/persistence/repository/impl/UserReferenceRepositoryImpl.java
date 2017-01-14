package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.UserReference;
import com.sap.jnc.marketing.persistence.repository.UserReferenceRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserReferenceRepositoryImpl extends SimpleJpaRepository<UserReference, Long> implements UserReferenceRepository {

	@Autowired
	public UserReferenceRepositoryImpl(EntityManager em) {
		super(UserReference.class, em);
	}
}

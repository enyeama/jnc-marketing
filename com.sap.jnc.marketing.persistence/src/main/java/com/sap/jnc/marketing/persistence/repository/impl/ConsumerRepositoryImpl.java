package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.Consumer;
import com.sap.jnc.marketing.persistence.repository.ConsumerRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConsumerRepositoryImpl extends SimpleJpaRepository<Consumer, String> implements ConsumerRepository {

	@Autowired
	public ConsumerRepositoryImpl(EntityManager em) {
		super(Consumer.class, em);
	}
}
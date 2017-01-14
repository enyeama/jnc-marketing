package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.Exhibition;
import com.sap.jnc.marketing.persistence.repository.ExhibitionRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExhibitionRepositoryImpl extends SimpleJpaRepository<Exhibition, Long> implements ExhibitionRepository {

	@Autowired
	public ExhibitionRepositoryImpl(EntityManager em) {
		super(Exhibition.class, em);
	}
}

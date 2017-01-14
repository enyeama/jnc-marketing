package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.ExhibitionType;
import com.sap.jnc.marketing.persistence.repository.ExhibitionTypeRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExhibitionTypeRepositoryImpl extends SimpleJpaRepository<ExhibitionType, Long> implements ExhibitionTypeRepository {

	@Autowired
	public ExhibitionTypeRepositoryImpl(EntityManager em) {
		super(ExhibitionType.class, em);
	}
}
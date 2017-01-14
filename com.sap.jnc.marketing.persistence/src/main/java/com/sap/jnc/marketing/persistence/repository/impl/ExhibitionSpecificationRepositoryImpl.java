package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.ExhibitionSpecification;
import com.sap.jnc.marketing.persistence.repository.ExhibitionSpecificationRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExhibitionSpecificationRepositoryImpl extends SimpleJpaRepository<ExhibitionSpecification, Long> implements ExhibitionSpecificationRepository {

	@Autowired
	public ExhibitionSpecificationRepositoryImpl(EntityManager em) {
		super(ExhibitionSpecification.class, em);
	}
}
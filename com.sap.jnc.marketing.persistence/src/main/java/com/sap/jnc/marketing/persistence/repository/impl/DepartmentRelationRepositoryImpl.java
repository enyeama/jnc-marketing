package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.DepartmentRelation;
import com.sap.jnc.marketing.persistence.repository.DepartmentRelationRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DepartmentRelationRepositoryImpl extends SimpleJpaRepository<DepartmentRelation, Long> implements DepartmentRelationRepository {

	@Autowired
	public DepartmentRelationRepositoryImpl(EntityManager em) {
		super(DepartmentRelation.class, em);
	}
}
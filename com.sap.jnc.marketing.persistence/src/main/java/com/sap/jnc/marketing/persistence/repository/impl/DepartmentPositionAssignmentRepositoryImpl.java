package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.DepartmentPositionAssignment;
import com.sap.jnc.marketing.persistence.repository.DepartmentPositionAssignmentRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DepartmentPositionAssignmentRepositoryImpl extends SimpleJpaRepository<DepartmentPositionAssignment, Long> implements DepartmentPositionAssignmentRepository {

	@Autowired
	public DepartmentPositionAssignmentRepositoryImpl(EntityManager em) {
		super(DepartmentPositionAssignment.class, em);
	}

}
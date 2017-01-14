package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.RegionPositionAssignment;
import com.sap.jnc.marketing.persistence.repository.RegionPositionAssignmentRepository;

/**
 * @author I300934 Ray Lv
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class RegionPositionAssignmentRepositoryImpl extends SimpleJpaRepository<RegionPositionAssignment, Long> implements RegionPositionAssignmentRepository {

	@Autowired
	public RegionPositionAssignmentRepositoryImpl(EntityManager em) {
		super(RegionPositionAssignment.class, em);
	}
}

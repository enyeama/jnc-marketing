package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.JobPositionAssignment;
import com.sap.jnc.marketing.persistence.model.JobPositionAssignment_;
import com.sap.jnc.marketing.persistence.repository.JobPositionAssignmentRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JobPositionAssignmentRepositoryImpl extends SimpleJpaRepository<JobPositionAssignment, Long> implements JobPositionAssignmentRepository {

	@Autowired
	public JobPositionAssignmentRepositoryImpl(EntityManager em) {
		super(JobPositionAssignment.class, em);
	}

	public JobPositionAssignment findOneByPosition(String postionId) {
		return super.findOne((root, query, cb) -> {
			return cb.equal(root.get(JobPositionAssignment_.positionExternalId), postionId);
		});
	}

}
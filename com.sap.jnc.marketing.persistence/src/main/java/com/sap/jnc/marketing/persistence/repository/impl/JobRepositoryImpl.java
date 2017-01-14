package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.Job;
import com.sap.jnc.marketing.persistence.repository.JobRepository;

/**
 * @author I071053 Diouf Du
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class JobRepositoryImpl extends SimpleJpaRepository<Job, Long> implements JobRepository {

	@Autowired
	public JobRepositoryImpl(EntityManager em) {
		super(Job.class, em);
	}
}

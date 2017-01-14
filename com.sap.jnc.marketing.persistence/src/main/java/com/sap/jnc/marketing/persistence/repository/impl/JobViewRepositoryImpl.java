package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.JobView;
import com.sap.jnc.marketing.persistence.repository.JobViewRepository;

/**
 * @author I071053 Diouf Du
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class JobViewRepositoryImpl extends SimpleJpaRepository<JobView, String> implements JobViewRepository {

	@Autowired
	public JobViewRepositoryImpl(EntityManager em) {
		super(JobView.class, em);
	}
}

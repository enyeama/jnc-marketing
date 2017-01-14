package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.UserReferenceToEmployee;
import com.sap.jnc.marketing.persistence.repository.UserReferenceToEmployeeRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserReferenceToEmployeeImpl extends SimpleJpaRepository<UserReferenceToEmployee, Long> implements UserReferenceToEmployeeRepository {

	@Autowired
	public UserReferenceToEmployeeImpl(EntityManager em) {
		super(UserReferenceToEmployee.class, em);
	}
}

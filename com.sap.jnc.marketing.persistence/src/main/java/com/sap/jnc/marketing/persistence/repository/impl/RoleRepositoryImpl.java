package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.Role_;
import com.sap.jnc.marketing.persistence.repository.RoleRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class RoleRepositoryImpl extends GeneralJpaRepositoryImpl<Role, Role_, Long> implements RoleRepository {

	@Autowired
	public RoleRepositoryImpl(EntityManager em) {
		super(Role.class, em);
	}

	@Override
	@Transactional(readOnly=true)
	public Role findOneByName(String name) {
		return super.findOne((root, query, cb) -> {
			return cb.equal(root.get(Role_.name), name);
		});
	}
}

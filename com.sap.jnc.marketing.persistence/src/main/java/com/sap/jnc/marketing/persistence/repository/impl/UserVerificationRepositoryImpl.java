package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import com.sap.jnc.marketing.persistence.enumeration.ExternalUserRoleType;
import com.sap.jnc.marketing.persistence.model.Terminal_;
import com.sap.jnc.marketing.persistence.model.UserVerification_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.UserVerification;
import com.sap.jnc.marketing.persistence.repository.UserVerificationRepository;

import java.util.List;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserVerificationRepositoryImpl extends SimpleJpaRepository<UserVerification, Long> implements UserVerificationRepository {

	@Autowired
	public UserVerificationRepositoryImpl(EntityManager em) {
		super(UserVerification.class, em);
	}

	@Override
	public List<UserVerification> findOneByPhone(String phoneNumber, String userName) {
		return super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(UserVerification_.mobile), phoneNumber), cb.equal(root.get(UserVerification_.name), userName));
		});
	}

	@Override
	public List<UserVerification> findAllByMobile(String mobile) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(UserVerification_.mobile), mobile);
		});
	}

	@Override
	public List<UserVerification> findAllByReferenceId(Long id, ExternalUserRoleType type) {
		return super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(UserVerification_.referenceId), id), cb.equal(root.get(UserVerification_.roleType), type));
		});
	}
}

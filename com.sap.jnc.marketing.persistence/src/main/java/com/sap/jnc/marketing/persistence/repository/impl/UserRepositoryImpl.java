package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.LoginAccount_;
import com.sap.jnc.marketing.persistence.model.Privilege;
import com.sap.jnc.marketing.persistence.model.Privilege_;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.Role_;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.model.User_;
import com.sap.jnc.marketing.persistence.model.WechatAccount_;
import com.sap.jnc.marketing.persistence.repository.UserRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class UserRepositoryImpl extends SimpleJpaRepository<User, Long> implements UserRepository {

	@Autowired
	public UserRepositoryImpl(EntityManager em) {
		super(User.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByUserName(String userName) {
		return this.findAll(
				(root, query, cb) -> cb.equal(root.get(User_.loginAccount).get(LoginAccount_.userName), userName));
	}

	@Override
	@Transactional(readOnly = true)
	public User findOneByLoginKeyword(String loginKeyword) {
		return super.findOne((root, query, cb) -> {
			query.distinct(true);
			// Fetch all roles and privileges
			root.fetch(User_.roles, JoinType.LEFT).fetch(Role_.privileges, JoinType.LEFT)
					.fetch(Privilege_.children, JoinType.LEFT).fetch(Privilege_.parent, JoinType.LEFT);
			// Fetch all roles and privileges
			root.fetch(User_.userReferences, JoinType.LEFT);
			// Login with user name, phone or openId
			return cb.or(
					// User name
					cb.equal(root.get(User_.loginAccount).get(LoginAccount_.userName), loginKeyword),
					// Phone
					cb.equal(root.get(User_.loginAccount).get(LoginAccount_.mobile), loginKeyword),
					// Wechat open id
					cb.equal(root.get(User_.wechatAccount).get(WechatAccount_.openId), loginKeyword));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public User findOneByEmployeeProfile(String name, String idCardNo, String mobile, String externalEmployeeId) {
		return super.findOne((root, query, cb) -> {
			return cb.and(
					// name
					cb.equal(root.get(User_.name), name),
					// Id Card No
					cb.equal(root.get(User_.loginAccount).get(LoginAccount_.IdCardNumber), idCardNo),
					// Mobile
					cb.equal(root.get(User_.loginAccount).get(LoginAccount_.mobile), mobile),
					// Employee external id
					cb.equal(root.get(User_.loginAccount).get(LoginAccount_.externalEmployeeId), externalEmployeeId));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public User findOneByOpenId(String openId) {
		return super.findOne((root, query, cb) -> {
			query.distinct(true);
			// Fetch all roles and privileges
			final Fetch<Role, Privilege> fetchPrivilege = root.fetch(User_.roles, JoinType.LEFT).fetch(Role_.privileges,
					JoinType.LEFT);
			fetchPrivilege.fetch(Privilege_.parent, JoinType.LEFT);
			fetchPrivilege.fetch(Privilege_.children, JoinType.LEFT);
			// Fetch all roles and privileges
			root.fetch(User_.userReferences, JoinType.LEFT);
			// Login with user name, phone or openId
			return cb.equal(root.get(User_.wechatAccount).get(WechatAccount_.openId), openId);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAllByUserName(String username) {
		return super.findAll((root, query, cb) -> {
			root.fetch(User_.roles).fetch(Role_.privileges);
			return cb.equal(root.get(User_.loginAccount).get(LoginAccount_.userName), username);
		});
	}
}

package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.model.Audit;
import com.sap.jnc.marketing.persistence.model.Audit_;
import com.sap.jnc.marketing.persistence.model.Employee_;
import com.sap.jnc.marketing.persistence.repository.AuditRepository;

/**
 * @author C5231393 Xu Xiaolei
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuditRepositoryImpl extends SimpleJpaRepository<Audit, Long> implements AuditRepository {

	@Autowired
	public AuditRepositoryImpl(EntityManager em) {
		super(Audit.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public Audit findOneByAuditId(final Long id) {
		return super.findOne((root, query, cb) -> {
			return cb.and(
				// Id
				cb.equal(root.get(Audit_.id), id));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Audit> findAllByAuditIds(List<Long> idsList) {
		return super.findAll((root, query, cb) -> {
			final Path<Long> path = root.get(Audit_.id);
			return path.in(idsList);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Audit> findAllWithItemAndPictures(Long id) {
		final List<Audit> result = super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(Audit_.items, JoinType.LEFT);
			root.fetch(Audit_.pictures, JoinType.LEFT);
			return cb.equal(root.get(Audit_.id), id);
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Long countByTypeAndStatus(String auditorId, AuditType auditType, AuditStatus auditStatus) {
		return super.count((root, query, cb) -> {
			if (StringUtils.isBlank(auditorId) ||StringUtils.isBlank(auditType.getLabel()) || StringUtils.isBlank(auditStatus.getLabel())) {
				return null;
			}
			return cb.and(cb.equal(root.get(Audit_.type), auditType),
					cb.equal(root.get(Audit_.status), auditStatus),
					cb.equal(root.join(Audit_.auditor).get(Employee_.id), auditorId));
		});
	}

}
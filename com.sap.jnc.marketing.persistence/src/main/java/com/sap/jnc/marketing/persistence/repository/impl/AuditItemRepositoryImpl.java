package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.AuditItem;
import com.sap.jnc.marketing.persistence.model.AuditItem_;
import com.sap.jnc.marketing.persistence.model.Audit_;
import com.sap.jnc.marketing.persistence.repository.AuditItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuditItemRepositoryImpl extends SimpleJpaRepository<AuditItem, Long> implements AuditItemRepository {

	@Autowired
	public AuditItemRepositoryImpl(EntityManager em) {
		super(AuditItem.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuditItem> findAllByAuditId(Long id) {
		return super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.join(AuditItem_.audit, JoinType.LEFT).get(Audit_.id), id));
		});
	}

}

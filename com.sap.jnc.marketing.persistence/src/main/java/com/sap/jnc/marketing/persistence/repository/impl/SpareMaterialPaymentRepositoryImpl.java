package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialPaymentSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Position_;
import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment;
import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment_;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialPaymentRepository;

/*
 * @author Kay, Du I326950
 */

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SpareMaterialPaymentRepositoryImpl extends SimpleJpaRepository<SpareMaterialPayment, Long> implements SpareMaterialPaymentRepository {

	@Autowired
	public SpareMaterialPaymentRepositoryImpl(EntityManager em) {
		super(SpareMaterialPayment.class, em);
	}

	@Override
	public List<SpareMaterialPayment> findByIdAndPositionId(String id, String positionId) {
		return super.findAll((root, query, cb) -> {
			root.fetch(SpareMaterialPayment_.position, JoinType.LEFT);
			Predicate p1 = cb.equal(root.join(SpareMaterialPayment_.position).get(Position_.externalId), positionId);
			Predicate p2 = cb.equal(root.get(SpareMaterialPayment_.id), id);
			return cb.and(p1, p2);
		});
	}

	@Override
	public List<SpareMaterialPayment> findById(String id) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(SpareMaterialPayment_.id), id);
		});
	}

	@Override
	public List<SpareMaterialPayment> findByPositionId(String positionId) {
		return super.findAll((root, query, cb) -> {
			root.fetch(SpareMaterialPayment_.position, JoinType.LEFT);
			return cb.equal(root.join(SpareMaterialPayment_.position).get(Position_.externalId), positionId);
		}, new Sort(new Order(Direction.DESC, SpareMaterialPayment_.paymentDate.getName())));
	}

	@Override
	public Page<SpareMaterialPayment> pagedQuery(SpareMaterialPaymentSearchKeywordNode keywords, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			root.fetch(SpareMaterialPayment_.position, JoinType.LEFT);
			List<Predicate> predicates = new ArrayList<>();
			
			if (StringUtils.isNotBlank(keywords.getPositionId())) {
				predicates.add(cb.equal(root.join(SpareMaterialPayment_.position).get(Position_.externalId), keywords.getPositionId()));
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageRequest);
	}
}

package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialDeliverySearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.Position_;
import com.sap.jnc.marketing.persistence.model.ProductType_;
import com.sap.jnc.marketing.persistence.model.Product_;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery_;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialDeliveryRepository;

/*
 * @author Kay, Du I326950
 */

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SpareMaterialDeliveryRepositoryImpl extends SimpleJpaRepository<SpareMaterialDelivery, Long> implements SpareMaterialDeliveryRepository {

	@Autowired
	public SpareMaterialDeliveryRepositoryImpl(EntityManager em) {
		super(SpareMaterialDelivery.class, em);
	}

	@Override
	public Page<SpareMaterialDelivery> pagedQuery(SpareMaterialDeliverySearchKeywordNode spareMaterialDeliverySearchKeywordNode,
		PageRequest pageRequest) {
		return super
			.findAll((root, query, cb) -> {
				// Fetches
				root.fetch(SpareMaterialDelivery_.position, JoinType.LEFT);
				root.fetch(SpareMaterialDelivery_.product, JoinType.LEFT);
				root.fetch(SpareMaterialDelivery_.product, JoinType.LEFT).fetch(Product_.productType, JoinType.LEFT);

				// Predicates
				final List<Predicate> predicates = new ArrayList<>();
				if (StringUtils.isNotBlank(spareMaterialDeliverySearchKeywordNode.getPositionId())) {
					predicates.add(cb.like(root.join(SpareMaterialDelivery_.position).get(Position_.externalId), "%"
						+ spareMaterialDeliverySearchKeywordNode.getPositionId() + "%"));
				}
				if (StringUtils.isNotBlank(spareMaterialDeliverySearchKeywordNode.getMaterialId())) {
					predicates.add(cb.like(root.join(SpareMaterialDelivery_.product).get(Product_.id), "%"
						+ StringUtils.upperCase(spareMaterialDeliverySearchKeywordNode.getMaterialId()) + "%"));
				}
				if (spareMaterialDeliverySearchKeywordNode.getDeliveryStatus() != null) {
					predicates.add(cb.equal(root.get(SpareMaterialDelivery_.deliveryStatus), spareMaterialDeliverySearchKeywordNode.getDeliveryStatus()));
				}
				if (spareMaterialDeliverySearchKeywordNode.getStartDate() != null) {
					if (spareMaterialDeliverySearchKeywordNode.getEndDate() != null) {
						predicates.add(cb.between(root.get(SpareMaterialDelivery_.deliveryDate), spareMaterialDeliverySearchKeywordNode
							.getStartDate(), spareMaterialDeliverySearchKeywordNode.getEndDate()));
					}
					else {
						predicates.add(cb.greaterThanOrEqualTo(root.get(SpareMaterialDelivery_.deliveryDate), spareMaterialDeliverySearchKeywordNode
							.getStartDate()));
					}
				}
				if (spareMaterialDeliverySearchKeywordNode.getStartDate() == null && spareMaterialDeliverySearchKeywordNode.getEndDate() != null) {
					predicates.add(cb.lessThanOrEqualTo(root.get(SpareMaterialDelivery_.deliveryDate), spareMaterialDeliverySearchKeywordNode
						.getStartDate()));
				}
				if (spareMaterialDeliverySearchKeywordNode.getGiftListStatus() != null) {
					predicates.add(cb.equal(root.get(SpareMaterialDelivery_.giftListStatus), spareMaterialDeliverySearchKeywordNode
						.getGiftListStatus()));
				}
				if (StringUtils.isNotBlank(spareMaterialDeliverySearchKeywordNode.getMaterialType())) {
					predicates.add(cb.equal(root.join(SpareMaterialDelivery_.product).join(Product_.productType).get(ProductType_.externalId),
						spareMaterialDeliverySearchKeywordNode.getMaterialType()));
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}, pageRequest);
	}

	@Override
	public List<SpareMaterialDelivery> findPositionIds() {
		return super.findAll((root, query, cb) -> {
			Join<SpareMaterialDelivery, Position> joinPostion = root.join(SpareMaterialDelivery_.position, JoinType.LEFT);
			Subquery<Long> sbquery = query.subquery(Long.class);
			Root<Position> subRoot = sbquery.from(Position.class);

			sbquery.distinct(true);
			sbquery.select(subRoot.get(Position_.id));

			return cb.and(cb.in(joinPostion.get(Position_.id)).value(sbquery));
		});
	}
}

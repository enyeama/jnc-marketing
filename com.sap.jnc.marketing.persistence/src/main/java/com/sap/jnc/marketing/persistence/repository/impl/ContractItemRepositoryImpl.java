package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.criteria.contract.ContractAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Contract;
import com.sap.jnc.marketing.persistence.model.ContractItem;
import com.sap.jnc.marketing.persistence.model.ContractItem_;
import com.sap.jnc.marketing.persistence.model.Contract_;
import com.sap.jnc.marketing.persistence.model.Dealer_;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory_;
import com.sap.jnc.marketing.persistence.repository.ContractItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContractItemRepositoryImpl extends SimpleJpaRepository<ContractItem, Long> implements ContractItemRepository {

	@Autowired
	public ContractItemRepositoryImpl(EntityManager em) {
		super(ContractItem.class, em);
	}

	@Override
	public Page<ContractItem> advanceSearch(ContractAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			Fetch<ContractItem, Contract> fetchContract = root.fetch(ContractItem_.contract, JoinType.LEFT);
			fetchContract.fetch(Contract_.dealer, JoinType.LEFT);
			fetchContract.fetch(Contract_.cityManager, JoinType.LEFT);
			root.fetch(ContractItem_.channel, JoinType.LEFT);
			root.fetch(ContractItem_.dmsCategory, JoinType.LEFT);
			// TODO add fetch region to aviod n+1

			// Predicates
			final List<Predicate> predicates = new ArrayList<>(5);

			Join<ContractItem, Contract> joinContract = root.join(ContractItem_.contract, JoinType.LEFT);

			if (StringUtils.isNotBlank(searchCriteria.getContractId())) {
				predicates.add(cb.equal(joinContract.get(Contract_.externalId), searchCriteria.getContractId()));
			}

			if (StringUtils.isNotBlank(searchCriteria.getDealerId())) {
				predicates.add(cb.equal(joinContract.join(Contract_.dealer, JoinType.LEFT).get(Dealer_.externalId), searchCriteria.getDealerId()));
			}
			// add position id
			if (StringUtils.isNotBlank(searchCriteria.getPositionId())) {
				predicates.add(cb.equal(joinContract.join(Contract_.cityManager, JoinType.LEFT).get(PositionView_.externalId), searchCriteria
					.getPositionId()));
			}

			if (StringUtils.isNotBlank(searchCriteria.getProductDmsCategoryId())) {
				predicates.add(cb.equal(root.join(ContractItem_.dmsCategory, JoinType.LEFT).get(ProductDmsCategory_.id), searchCriteria
					.getProductDmsCategoryId()));
			}

			if (searchCriteria.getStatus() != null) {
				predicates.add(cb.equal(joinContract.get(Contract_.status), searchCriteria.getStatus()));
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageRequest);
	}
}

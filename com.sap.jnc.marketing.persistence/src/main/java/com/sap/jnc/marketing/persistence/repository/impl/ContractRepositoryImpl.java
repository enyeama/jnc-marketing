package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.enumeration.ProductDmsCategoryLevel;
import com.sap.jnc.marketing.persistence.model.Channel_;
import com.sap.jnc.marketing.persistence.model.Contract;
import com.sap.jnc.marketing.persistence.model.ContractItem_;
import com.sap.jnc.marketing.persistence.model.Contract_;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory_;
import com.sap.jnc.marketing.persistence.model.Product_;
import com.sap.jnc.marketing.persistence.model.Region_;
import com.sap.jnc.marketing.persistence.repository.ContractRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContractRepositoryImpl extends SimpleJpaRepository<Contract, Long> implements ContractRepository {

	@Autowired
	public ContractRepositoryImpl(EntityManager em) {
		super(Contract.class, em);
	}

	@Override
	public List<Contract> findContratsByManager(String id) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Contract_.cityManager, JoinType.LEFT);
			root.fetch(Contract_.items,JoinType.LEFT).fetch(ContractItem_.dmsCategory,JoinType.LEFT).fetch(ProductDmsCategory_.products);
			root.fetch(Contract_.dealer,JoinType.LEFT);
			return cb.and(cb.equal(root.join(Contract_.cityManager).get(PositionView_.externalId), id));
		});

	}

	@Override
	public List<Contract> findContractByExternalIds(Collection<String> contractIdSet) {
		return super.findAll((root, query, cb) -> {
			return root.get(Contract_.externalId).in(contractIdSet);
		});
	}

	@Override
	public Collection<Contract> findContractByTerminalOrderInfo(Long channelId, Long productId, Long regionId) {
		List<Contract> contracts = super.findAll((root, query, cb) -> {
			query.distinct(true);
			Join contractItemJoin = root.join(Contract_.items);
			Join channelJoin = contractItemJoin.join(ContractItem_.channel);
			Join regionJoin = contractItemJoin.join(ContractItem_.regions);
			Join dmsCategoryJoin = contractItemJoin.join(ContractItem_.dmsCategory);
			Join productJoin = dmsCategoryJoin.join(ProductDmsCategory_.products);
			return cb.and(cb.equal(channelJoin.get(Channel_.id), channelId), cb.equal(regionJoin.get(Region_.id), regionId), cb.equal(productJoin.get(
				Product_.id), productId), cb.equal(dmsCategoryJoin.get(ProductDmsCategory_.level), ProductDmsCategoryLevel.THIRD_LEVEL));
		});
		return contracts;
	}
}

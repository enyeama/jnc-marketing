/**
 * 
 */
package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.BanquetRebateConfig;
import com.sap.jnc.marketing.persistence.model.BanquetRebateConfig_;
import com.sap.jnc.marketing.persistence.model.Product_;
import com.sap.jnc.marketing.persistence.repository.BanquetRebateConfigRepository;

/**
 * @author Joel.Cheng I310645
 */
@Repository
@Transactional
public class BanquetRebateConfigRepositoryImpl extends SimpleJpaRepository<BanquetRebateConfig, Long>
		implements BanquetRebateConfigRepository {

	@Autowired
	public BanquetRebateConfigRepositoryImpl(EntityManager em) {
		super(BanquetRebateConfig.class, em);
	}

	@Override
	public List<BanquetRebateConfig> queryByScanedProduct(Banquet banquet, String productId) {
		List<BanquetRebateConfig> results = this.findAll((root, query, cb) -> {
			// root.fetch(BanquetRebateConfig_.product, JoinType.LEFT);
			return cb.and(cb.equal(root.get(BanquetRebateConfig_.applicationType), banquet.getApplicationType()),
					cb.equal(root.get(BanquetRebateConfig_.channel), banquet.getMaterialChannel()),
					cb.equal(root.join(BanquetRebateConfig_.product, JoinType.LEFT).get(Product_.id), productId));
		});

		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BanquetRebateConfig> findByScanedProduct(BanquetApplicationType banquetApplicationType) {
		List<BanquetRebateConfig> results = this.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(BanquetRebateConfig_.product, JoinType.LEFT).fetch(Product_.productErpCategories, JoinType.LEFT);
			root.fetch(BanquetRebateConfig_.channel, JoinType.LEFT);
			return cb.and(cb.equal(root.get(BanquetRebateConfig_.applicationType), banquetApplicationType));
			// cb.equal(root.join(BanquetRebateConfig_.product,
			// JoinType.LEFT).join(Product_.productErpCategories,
			// JoinType.LEFT).get(ProductErpCategory_.levelNumber), "820"));
		});
		return results;
	}

}

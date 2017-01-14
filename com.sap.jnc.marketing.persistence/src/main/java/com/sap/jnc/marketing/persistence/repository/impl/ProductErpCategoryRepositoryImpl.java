package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.ProductErpCategory;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory_;
import com.sap.jnc.marketing.persistence.repository.ProductErpCategoryRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductErpCategoryRepositoryImpl extends SimpleJpaRepository<ProductErpCategory, Long> implements ProductErpCategoryRepository {

	@Autowired
	public ProductErpCategoryRepositoryImpl(EntityManager em) {
		super(ProductErpCategory.class, em);
	}

	@Override
	public List<ProductErpCategory> findBySalesCategory(String externalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductErpCategory> findAllFourthCategory() {
		List<ProductErpCategory> results = this.findAll((root, query, cb) -> {
			return cb.equal(root.get(ProductErpCategory_.levelNumber), ProductErpCategory.PRODUCT_LEVEL_4_ID);
		});

		return results;
	}
}

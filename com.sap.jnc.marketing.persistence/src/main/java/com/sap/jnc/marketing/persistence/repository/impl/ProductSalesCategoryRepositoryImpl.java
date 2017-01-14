package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;
import com.sap.jnc.marketing.persistence.model.ProductSalesCategory_;
import com.sap.jnc.marketing.persistence.repository.ProductSalesCategoryRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductSalesCategoryRepositoryImpl extends SimpleJpaRepository<ProductSalesCategory, Long> implements ProductSalesCategoryRepository {

	@Autowired
	public ProductSalesCategoryRepositoryImpl(EntityManager em) {
		super(ProductSalesCategory.class, em);
	}

	@Override
	public ProductSalesCategory findProductSalesCategoryByExternalId(String id) {
		return super.findOne((root, query, cb) -> {
			return cb.equal(root.get(ProductSalesCategory_.externalId), id);
		});
	}
}

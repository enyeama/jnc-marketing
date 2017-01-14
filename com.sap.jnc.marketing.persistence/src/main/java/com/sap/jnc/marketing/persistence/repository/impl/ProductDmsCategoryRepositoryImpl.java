package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.enumeration.ProductDmsCategoryLevel;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory_;
import com.sap.jnc.marketing.persistence.model.ProductSalesCategory_;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductDmsCategoryRepositoryImpl extends SimpleJpaRepository<ProductDmsCategory, Long> implements ProductDmsCategoryRepository {
	@Autowired
	public ProductDmsCategoryRepositoryImpl(EntityManager em) {
		super(ProductDmsCategory.class, em);
	}

	@Override
	public List<ProductDmsCategory> findBySalesCategory(String salesCategorylId, ProductDmsCategoryLevel level) {
		return super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.join(ProductDmsCategory_.productSalesCategory).get(ProductSalesCategory_.externalId), salesCategorylId), cb
				.equal(root.get(ProductDmsCategory_.level), level));
		});
	}

	@Override
	public List<ProductDmsCategory> findDMSCategoryByIds(Collection<String> categoryIdSet) {
		return super.findAll((root, query, cb) -> {
			return root.get(ProductDmsCategory_.id).in(categoryIdSet);
		});
	}

	@Override
	public ProductDmsCategory findDMSCategoryById(String id) {
		return super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(ProductDmsCategory_.id), id));
		});
	}

	@Override
	public Page<ProductDmsCategory> findSalesDMSRelations(PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			root.fetch(ProductDmsCategory_.productSalesCategory,JoinType.LEFT);
			return cb.isNotNull(root.get(ProductDmsCategory_.productSalesCategory).get(ProductSalesCategory_.id));
		}, pageRequest);
	}
	
	@Override
	public List<ProductDmsCategory> findDMSCategoryByNAme(String name) {
		return super.findAll((root, query, cb) -> {
			root.fetch(ProductDmsCategory_.products,JoinType.LEFT);
			return cb.equal(root.get(ProductDmsCategory_.name),name);
		});
	}

	@Override
	public List<ProductDmsCategory> findDMSCategoryBySalesCategory(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

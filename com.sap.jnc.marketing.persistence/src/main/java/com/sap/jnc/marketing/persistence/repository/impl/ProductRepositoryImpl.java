package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.criteria.product.GeneralSearchNode;
import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.Channel_;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory_;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory_;
import com.sap.jnc.marketing.persistence.model.Product_;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductRepositoryImpl extends SimpleJpaRepository<Product, String> implements ProductRepository {

	@Autowired
	public ProductRepositoryImpl(EntityManager em) {
		super(Product.class, em);
	}

	@Override
	public List<Product> findForSalesAddOrder(String dmsCategoryId, Long channelId) {
		return super.findAll((root, query, cb) -> {

			Join<Product, ProductDmsCategory> dmsCategory = root.join(Product_.productDmsCategories, JoinType.LEFT);

			Subquery<String> sbquery = query.subquery(String.class);
			Root<Product> subRoot = sbquery.from(Product.class);
			//Join<Product, ProductErpCategory> erpCategory = subRoot.join(Product_.productErpCategories, JoinType.LEFT);
			Join<Product, Channel> channel = subRoot.join(Product_.channels, JoinType.LEFT);
			sbquery.select(subRoot.get(Product_.id));
			sbquery.where(cb.equal(channel.get(Channel_.id), channelId));
			return cb.and(cb.equal(dmsCategory.get(ProductDmsCategory_.id), dmsCategoryId), cb.in(root.get(Product_.id)).value(sbquery));
		});
	}

	@Override
	public Product findProductByExternalId(String id) {
		return super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Product_.id), id));
		});
	}

	public List<Product> findAll() {
		return super.findAll((root, query, cb) -> {
			// Fetches
			query.distinct(true);
			root.fetch(Product_.productGroup, JoinType.LEFT);
			root.fetch(Product_.productType, JoinType.LEFT);
			root.fetch(Product_.productDmsCategories, JoinType.LEFT);
			root.fetch(Product_.productErpCategories, JoinType.LEFT);
			root.fetch(Product_.channels, JoinType.LEFT);

			// Predicates
			final List<Predicate> predicates = new ArrayList<>();
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		});
	}

	@Override
	public Page<Product> advanceSearch(GeneralSearchNode searchNode) {
		return super.findAll((root, query, cb) -> {
			// Fetches
			query.distinct(true);
			root.fetch(Product_.productGroup, JoinType.LEFT);
			root.fetch(Product_.productType, JoinType.LEFT);
//			 root.fetch(Product_.productDmsCategories, JoinType.LEFT);
//			 root.fetch(Product_.productErpCategories, JoinType.LEFT);
//			 root.fetch(Product_.channels, JoinType.LEFT);

			// Predicates
			final List<Predicate> predicates = new ArrayList<>();
			Map<String, Object> filters = searchNode.getFilters();
			if (StringUtils.isNotBlank(MapUtils.getString(filters, "id"))) {
				predicates.add(cb.like(root.get(Product_.id), "%" + MapUtils.getString(filters, "id") + "%"));
			}
			if (StringUtils.isNotBlank(MapUtils.getString(filters, "name"))) {
				predicates.add(cb.like(root.get(Product_.name), "%" + MapUtils.getString(filters, "name") + "%"));
			}
			if (StringUtils.isNotBlank(MapUtils.getString(filters, "dmsCategoryId"))) {
				predicates.add(cb.equal(root.join(Product_.productDmsCategories).get(ProductDmsCategory_.id), MapUtils.getString(filters,
					"dmsCategoryId")));
			}
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, searchNode.getPageRequest());
	}

	@Override
	public List<Product> findByMaterialIds(Collection<String> materialIds) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Product_.productErpCategories);
			return root.get(Product_.id).in(materialIds);
		});
	}
}

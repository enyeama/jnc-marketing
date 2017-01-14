package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.ProductGroup;
import com.sap.jnc.marketing.persistence.repository.ProductGroupRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductGroupRepositoryImpl extends SimpleJpaRepository<ProductGroup, String> implements ProductGroupRepository {

	@Autowired
	public ProductGroupRepositoryImpl(EntityManager em) {
		super(ProductGroup.class, em);
	}

	public List<ProductGroup> findAll(boolean dependency) {
		if (dependency) {
			return super.findAll();
		}
		else {
			return super.findAll((root, query, cb) -> {
				root.fetch("name").fetch("id");
				return cb.and();
			});
		}
	}
}

package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.ProductType;
import com.sap.jnc.marketing.persistence.repository.ProductTypeRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductTypeRepositoryImpl extends SimpleJpaRepository<ProductType, Long> implements ProductTypeRepository {

	@Autowired
	public ProductTypeRepositoryImpl(EntityManager em) {
		super(ProductType.class, em);
	}
	
	public List<ProductType> findAll(boolean dependency) {
		if (dependency) {
			return super.findAll();
		}
		else {
			return super.findAll((root, query, cb) -> {
				root.fetch("externalId").fetch("externalId");
				return cb.and();
			});
		}
	}
}

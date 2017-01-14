package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.ProductGroup;

@NoRepositoryBean
public interface ProductGroupRepository extends JpaRepository<ProductGroup, String> {

	public List<ProductGroup> findAll(boolean dependency);
}

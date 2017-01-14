package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.ProductErpCategory;

@NoRepositoryBean
public interface ProductErpCategoryRepository extends JpaRepository<ProductErpCategory, Long> {

	List<ProductErpCategory> findBySalesCategory(String externalId);
	List<ProductErpCategory> findAllFourthCategory();
}

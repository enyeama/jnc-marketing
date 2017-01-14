package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;

@NoRepositoryBean
public interface ProductSalesCategoryRepository extends JpaRepository<ProductSalesCategory, Long> {
	ProductSalesCategory findProductSalesCategoryByExternalId(String id);
}

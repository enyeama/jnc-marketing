package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.product.GeneralSearchNode;
import com.sap.jnc.marketing.persistence.model.Product;

@NoRepositoryBean
public interface ProductRepository extends JpaRepository<Product, String> {
	public Product findProductByExternalId(String id);

	List<Product> findForSalesAddOrder(String dmsCategoryId, Long channelId);

	public Page<Product> advanceSearch(GeneralSearchNode searchNode);

	List<Product> findByMaterialIds(Collection<String> materialIds);

}

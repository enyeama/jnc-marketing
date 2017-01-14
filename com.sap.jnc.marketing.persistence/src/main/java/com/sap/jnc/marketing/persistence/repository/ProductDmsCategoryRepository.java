package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.enumeration.ProductDmsCategoryLevel;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;

@NoRepositoryBean
public interface ProductDmsCategoryRepository extends JpaRepository<ProductDmsCategory, Long> {

	List<ProductDmsCategory> findDMSCategoryBySalesCategory(Long id);
	ProductDmsCategory findDMSCategoryById(String id);
	Page<ProductDmsCategory> findSalesDMSRelations(PageRequest pageRequest);
	List<ProductDmsCategory> findBySalesCategory(String salesCategorylId, ProductDmsCategoryLevel level);
	List<ProductDmsCategory> findDMSCategoryByNAme(String name);

	List<ProductDmsCategory> findDMSCategoryByIds(Collection<String> categoryIdSet);
}

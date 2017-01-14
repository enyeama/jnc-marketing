package com.sap.jnc.marketing.service.relationmainten;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;

/**
 * @author Maggie Liu
 */
public interface SalesCategoryDMSService {

	List<ProductSalesCategory> findAllSalesCategoriesByCityManager();

	List<ProductDmsCategory> findAllProductDmsCategories();

	Page<ProductDmsCategory> getSalesDMSRelations(PageRequest pageRequest);

	boolean createSaleDMSRelation(String salesExternalId, String dmsId);

	boolean deleteRelations(String salesId, String dmsId);
}

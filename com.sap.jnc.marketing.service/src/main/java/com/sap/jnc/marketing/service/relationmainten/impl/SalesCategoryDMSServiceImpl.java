package com.sap.jnc.marketing.service.relationmainten.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;
import com.sap.jnc.marketing.persistence.repository.ContractRepository;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductSalesCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.RelationMaintenRepository;
import com.sap.jnc.marketing.service.relationmainten.SalesCategoryDMSService;

/**
 * @author Maggie Liu
 */
@Service
@Transactional
public class SalesCategoryDMSServiceImpl implements SalesCategoryDMSService {

	@Autowired
	RelationMaintenRepository relationMaintenRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	ProductSalesCategoryRepository productSalesCategoryRepository;

	@Autowired
	ProductDmsCategoryRepository productDmsCategoryRepository;

	@Override
	public List<ProductSalesCategory> findAllSalesCategoriesByCityManager() {
		return this.productSalesCategoryRepository.findAll();
	}

	@Override
	public List<ProductDmsCategory> findAllProductDmsCategories() {
		return this.productDmsCategoryRepository.findAll();
	}

	@Override
	public boolean createSaleDMSRelation(String salesExternalId, String dmsId) {
		final ProductSalesCategory productSalesCategory = this.productSalesCategoryRepository.findProductSalesCategoryByExternalId(salesExternalId);
		final ProductDmsCategory productDmsCategory = this.productDmsCategoryRepository.findDMSCategoryById(dmsId);
		if ((productDmsCategory != null) && (productSalesCategory != null)) {
			if(productDmsCategory.getProductSalesCategory()!=null){
				if (productDmsCategory.getProductSalesCategory().getExternalId().equals(productSalesCategory.getExternalId())) {
					return false;
				}
			}

			productDmsCategory.setProductSalesCategory(productSalesCategory);
			productSalesCategory.getProductDmsCategories().add(productDmsCategory);
			this.productSalesCategoryRepository.saveAndFlush(productSalesCategory);
			this.productDmsCategoryRepository.saveAndFlush(productDmsCategory);
			return true;
		}
		return false;
	}

	@Override
	public Page<ProductDmsCategory> getSalesDMSRelations(PageRequest pageRequest) {
		return this.productDmsCategoryRepository.findSalesDMSRelations(pageRequest);
	}

	@Override
	public boolean deleteRelations(String salesId, String dmsId) {
		final ProductSalesCategory productSalesCategory = this.productSalesCategoryRepository.findProductSalesCategoryByExternalId(salesId);
		final ProductDmsCategory productDmsCategory = this.productDmsCategoryRepository.findDMSCategoryById(dmsId);
		if ((productDmsCategory != null) && (productSalesCategory != null)) {
			productDmsCategory.setProductSalesCategory(null);
			productSalesCategory.getProductDmsCategories().remove(productDmsCategory);
			this.productSalesCategoryRepository.saveAndFlush(productSalesCategory);
			this.productDmsCategoryRepository.saveAndFlush(productDmsCategory);
			return true;
		}
		return false;
	}

}

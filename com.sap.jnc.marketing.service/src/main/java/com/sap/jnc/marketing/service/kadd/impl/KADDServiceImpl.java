package com.sap.jnc.marketing.service.kadd.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.kadd.KADDService;

@Service
@Transactional
public class KADDServiceImpl implements KADDService{

	@Autowired
	TerminalRepository terminalRepository;
	
	@Autowired
	ProductDmsCategoryRepository productDmsCategoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public List<Terminal> findKAByKASpecialExternalId(String externalId) {
		List<Terminal> terminals=terminalRepository.findByKASpecialExternalId(externalId);
		if(!CollectionUtils.isEmpty(terminals)){
			return terminals;
		}
		return Collections.emptyList();
	}

	@Override
	public List<ProductDmsCategory> findAllDMSCategorys() {
		List<ProductDmsCategory> productDmsCategories=productDmsCategoryRepository.findAll();
		if(!CollectionUtils.isEmpty(productDmsCategories)){
			return productDmsCategories;
		}
		return Collections.emptyList();
	}

	@Override
	public List<Terminal> findKAByKAName(String kaSpacialExternalId,String kaName) {
		List<Terminal> terminals= terminalRepository.findByKAName(kaSpacialExternalId,kaName);
		if(!CollectionUtils.isEmpty(terminals)){
			return terminals;
		}
		return Collections.emptyList();
	}

	@Override
	public List<Product> findProductsByDmsName(String dmsName) {
		List<ProductDmsCategory> productDmsCategorys=productDmsCategoryRepository.findDMSCategoryByNAme(dmsName);
		if(!CollectionUtils.isEmpty(productDmsCategorys)){
			ProductDmsCategory productDmsCategory=productDmsCategorys.get(0);
			List<Product> products=productDmsCategory.getProducts();
			if(!CollectionUtils.isEmpty(products)){
				return products;
			}
		}
		
		return Collections.emptyList();
	}
}

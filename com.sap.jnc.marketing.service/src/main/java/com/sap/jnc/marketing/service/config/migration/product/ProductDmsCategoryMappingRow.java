package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.impl.ProductRepositoryImpl;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.Mapping;
import com.sap.jnc.marketing.service.config.migration.MappingElement;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.validate.rule.DependencyRule;
import com.sap.jnc.marketing.service.migration.validate.rule.MappingExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

@Mapping(major=@MappingElement(entity=Product.class, repository=ProductRepository.class, prop="productDmsCategories"), 
		 slave=@MappingElement(entity=ProductDmsCategory.class, repository=ProductDmsCategoryRepository.class, prop="products"))
@UploadFile(fileType=FileType.EXCEL, sheetName="DMS三级大类对应物料编码", keys={"productId", "categoryId"}, rules={MappingExistRule.class})
@Caches(list={
	@Cacheable(entity=Product.class, repository=ProductRepositoryImpl.class, keys="id"),
	@Cacheable(entity=ProductDmsCategory.class, repository=ProductDmsCategoryRepository.class, keys="id")
})
public class ProductDmsCategoryMappingRow extends UploadRow {

	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop="categoryId", column=1, header="第三级大类", 
		field="id", dependency=true, dependencyEntity=ProductDmsCategory.class, dependencyKeys="id", 
		rules={NonBlankRule.class, DependencyRule.class})
	private String categoryId;
	
	@UploadField(prop="productId", column=2, header="物料编码", 
		field="id", dependency=true, dependencyEntity=Product.class, dependencyKeys="id", 
		rules={NonBlankRule.class, DependencyRule.class})
	private String productId;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}

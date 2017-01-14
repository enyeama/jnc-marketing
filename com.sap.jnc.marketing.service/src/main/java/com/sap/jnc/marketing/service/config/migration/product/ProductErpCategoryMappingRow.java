package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory;
import com.sap.jnc.marketing.persistence.repository.ProductErpCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.impl.ProductErpCategoryRepositoryImpl;
import com.sap.jnc.marketing.persistence.repository.impl.ProductRepositoryImpl;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.Mapping;
import com.sap.jnc.marketing.service.config.migration.MappingElement;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.validate.rule.MappingExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

/**
 * 物料和14大类的映射关系
 * 
 * @author I322359
 */
@Mapping(major=@MappingElement(entity=Product.class, repository=ProductRepository.class, prop="productErpCategories"), 
         slave=@MappingElement(entity=ProductErpCategory.class, repository=ProductErpCategoryRepository.class, prop="products"))
@UploadFile(fileType=FileType.EXCEL, sheetName="物料编码和14级大类", keys={"productId", "levelNumber", "categoryId"}, rules={MappingExistRule.class})
@Caches(list={
	@Cacheable(entity=Product.class, repository=ProductRepositoryImpl.class, keys="id"),
	@Cacheable(entity=ProductErpCategory.class, repository=ProductErpCategoryRepositoryImpl.class, keys={"levelNumber", "categoryId"})
})
public class ProductErpCategoryMappingRow extends UploadRow {
	
	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop="productId", column=1, header="物料编码", 
		field="id", dependency=true, dependencyEntity=Product.class, dependencyKeys="id", 
		rules={NonBlankRule.class})
	private String productId;
	
	@UploadField(prop="levelNumber", column=2, header="物料分类级别", 
		field="levelNumber", dependency=true, dependencyEntity=ProductErpCategory.class, dependencyKeys="levelNumber",
		rules={NonBlankRule.class})
	private String levelNumber;
	
	@UploadField(prop="categoryId", column=3, header="分类值", 
		field="categoryId", dependency=true, dependencyEntity=ProductErpCategory.class, dependencyKeys="categoryId",
		rules={NonBlankRule.class})
	private String categoryId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(String levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}

package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductGroup;
import com.sap.jnc.marketing.persistence.model.ProductType;
import com.sap.jnc.marketing.persistence.repository.ProductGroupRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.ProductTypeRepository;
import com.sap.jnc.marketing.persistence.repository.impl.ProductRepositoryImpl;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.validate.rule.DependencyRule;
import com.sap.jnc.marketing.service.migration.validate.rule.EntityExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

/**
 * 物料信息
 * @author I322359
 *
 */
@UploadFile(fileType = FileType.EXCEL, sheetName = "物料主数据基本信息", 
	entity = Product.class, repository = ProductRepositoryImpl.class, keys={"id"},
	rules={EntityExistRule.class})
@Caches(list = { @Cacheable(entity = ProductGroup.class, repository = ProductGroupRepository.class, keys = { "id" }),
	@Cacheable(entity = ProductType.class, repository = ProductTypeRepository.class, keys = { "externalId" }),
	@Cacheable(entity = Product.class, repository = ProductRepository.class, keys = { "id" }) })
public class ProductRow extends UploadRow {

	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop = "id", header = "物料编码", column = 1, field = "id", 
		rules = { NonBlankRule.class })
	private String id;

	@UploadField(prop = "name", header = "物料名称", column = 2, field = "name", 
		rules = { NonBlankRule.class })
	private String name;

	@UploadField(
		prop = "productGroup", header = "物料组", column = 3, field = "productGroup", 
		dependency = true, dependencyEntity = ProductGroup.class, dependencyKeys="id", 
		rules = {NonBlankRule.class, DependencyRule.class })
	private String productGroup;

	@UploadField(
		prop = "productType", header = "物料类型", column = 4, field = "productType", 
		dependency = true, dependencyEntity = ProductType.class, dependencyKeys="externalId",
		rules = {NonBlankRule.class, DependencyRule.class })
	private String productType;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}

package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.enumeration.ProductDmsCategoryLevel;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.validate.rule.EntityExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

/**
 * 物料DMS分类
 * @author I322359
 *
 */
@UploadFile(fileType = FileType.EXCEL, sheetName = "DMS三级大类", 
entity = ProductDmsCategory.class, repository = ProductDmsCategoryRepository.class, keys={"id"}, parentKeys={"parentId"},
rules={EntityExistRule.class})
@Caches(list = { @Cacheable(entity = ProductDmsCategory.class, repository = ProductDmsCategoryRepository.class, keys = { "id" }) })
public class ProductDmsCategoryRow extends UploadRow {
	
	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop = "id", header="DMS三级大类", column = 1, field = "id", rules = { NonBlankRule.class })
	private String id;

	@UploadField(prop = "level", header="DMS分类级别", column = 2, field = "level", rules = { NonBlankRule.class })
	private ProductDmsCategoryLevel level;
	
	@UploadField(prop = "name", header="DMS三级大类描述", column = 3, field = "name", rules = { NonBlankRule.class })
	private String name;
	
	@UploadField(prop = "parentId", header="上级类别ID", column = 4, field = "parentCategory", dependency=true, dependencyKeys="id", dependencyEntity=ProductDmsCategory.class)
	private String parentId;
	
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

	public ProductDmsCategoryLevel getLevel() {
		return level;
	}

	public void setLevel(ProductDmsCategoryLevel level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}

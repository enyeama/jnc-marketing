package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.model.ProductErpCategory;
import com.sap.jnc.marketing.persistence.repository.ProductErpCategoryRepository;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.validate.rule.EntityExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

/**
 * 物料ERP分类
 * 
 * @author I322359
 */
@UploadFile(fileType = FileType.EXCEL, sheetName = "14级分类编码", 
	entity = ProductErpCategory.class, repository = ProductErpCategoryRepository.class, keys = {"levelNumber", "categoryId"}, 
	rules = { EntityExistRule.class })
@Caches(list = { @Cacheable(entity = ProductErpCategory.class, repository = ProductErpCategoryRepository.class, keys = { "levelNumber", "categoryId" }), })
public class ProductErpCategoryRow extends UploadRow {

	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop = "levelNumber", header = "分类级别", column = 1, field = "levelNumber", rules = { NonBlankRule.class })
	private String levelNumber;

	@UploadField(prop = "levelName", header = "分类级别描述", column = 2, field = "levelName", rules = { NonBlankRule.class })
	private String levelName;

	@UploadField(prop = "categoryId", header = "分类值", column = 3, field = "categoryId", rules = { NonBlankRule.class })
	private String categoryId;

	@UploadField(prop = "categoryName", header = "分类值描述", column = 4, field = "categoryName", rules = { NonBlankRule.class })
	private String categoryName;

	public String getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(String levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}

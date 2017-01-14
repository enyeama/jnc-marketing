package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.model.ProductGroup;
import com.sap.jnc.marketing.persistence.repository.ProductGroupRepository;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.validate.rule.EntityExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

/**
 * 物料组
 * @author I322359
 *
 */
@UploadFile(fileType = FileType.EXCEL, sheetName = "物料组", entity = ProductGroup.class, repository = ProductGroupRepository.class, keys = {
	"id" }, rules = { EntityExistRule.class })
@Caches(list = { @Cacheable(entity = ProductGroup.class, repository = ProductGroupRepository.class, keys = { "id" })})
public class ProductGroupRow extends UploadRow {

	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop = "id", header="物料组编码", column = 1, field = "id", rules = { NonBlankRule.class })
	private String id;

	@UploadField(prop = "name", header="物料组描述", column = 2, field = "name", rules = { NonBlankRule.class })
	private String name;

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
}

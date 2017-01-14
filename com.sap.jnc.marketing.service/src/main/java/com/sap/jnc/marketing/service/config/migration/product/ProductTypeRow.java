package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.model.ProductType;
import com.sap.jnc.marketing.persistence.repository.ProductTypeRepository;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.validate.rule.EntityExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

/**
 * 物料类型
 * @author I322359
 *
 */
@UploadFile(fileType = FileType.EXCEL, sheetName = "物料类型", 
	entity = ProductType.class, repository = ProductTypeRepository.class, keys={"externalId"},
	rules={EntityExistRule.class}
)
@Caches(list={
	@Cacheable(entity = ProductType.class, repository = ProductTypeRepository.class, keys = { "externalId" })
})
public class ProductTypeRow extends UploadRow {

	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop = "externalId", column = 1, header = "物料类型", field = "externalId", rules={NonBlankRule.class})
	private String externalId;

	@UploadField(prop = "name", column = 2, header = "物料类型描述", field = "name")
	private String name;

	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

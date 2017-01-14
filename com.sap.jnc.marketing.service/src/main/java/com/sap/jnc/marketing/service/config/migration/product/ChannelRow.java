package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.repository.ChannelRepository;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.validate.rule.EntityExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

/**
 * 渠道Row
 * 
 * @author I322359
 */

@UploadFile(fileType = FileType.EXCEL, sheetName = "渠道", 
	entity = Channel.class, repository = ChannelRepository.class, keys = {"externalId" }, 
	rules = { EntityExistRule.class })
@Caches(list = { @Cacheable(entity = Channel.class, repository = ChannelRepository.class, keys = { "externalId" }) })
public class ChannelRow extends UploadRow {
	
	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop = "externalId", header="渠道编码", column = 1, field = "externalId", rules = { NonBlankRule.class })
	private String externalId;
	
	@UploadField(prop = "name", header="渠道描述", column = 2, field = "name", rules = { NonBlankRule.class })
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

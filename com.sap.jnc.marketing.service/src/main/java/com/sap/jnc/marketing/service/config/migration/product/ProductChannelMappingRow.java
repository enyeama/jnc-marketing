package com.sap.jnc.marketing.service.config.migration.product;

import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.repository.impl.ChannelRepositoryImpl;
import com.sap.jnc.marketing.persistence.repository.impl.ProductRepositoryImpl;
import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.Caches;
import com.sap.jnc.marketing.service.config.migration.Mapping;
import com.sap.jnc.marketing.service.config.migration.MappingElement;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.config.migration.UploadFile.FileType;
import com.sap.jnc.marketing.service.migration.validate.rule.MappingExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

/**
 * 物料和渠道的映射关系
 * 
 * @author I322359
 */
@Mapping(major=@MappingElement(entity=Product.class, repository=ProductRepositoryImpl.class, prop="channels"), 
		 slave=@MappingElement(entity=Channel.class, repository=ChannelRepositoryImpl.class, prop="products"))
@UploadFile(fileType=FileType.EXCEL, sheetName="物料编码和渠道对应关系", keys={"productId", "channelId"}, rules={MappingExistRule.class})
@Caches(list={
	@Cacheable(entity=Product.class, repository=ProductRepositoryImpl.class, keys="id"),
	@Cacheable(entity=Channel.class, repository=ChannelRepositoryImpl.class, keys="externalId")
})
public class ProductChannelMappingRow extends UploadRow {

	@UploadField(prop="operation", column=0, header="操作标识")
	private String operation;
	
	@UploadField(prop="productId", column=1, header="物料编码", 
		field="id", dependency=true, dependencyEntity=Product.class, dependencyKeys="id", 
		rules={NonBlankRule.class})
	private String productId;
	
	@UploadField(prop="channelId", column=2, header="渠道编码", 
		field="externalId", dependency=true, dependencyEntity=Channel.class, dependencyKeys="externalId",
		rules={NonBlankRule.class})
	private String channelId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
}

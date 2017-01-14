package com.sap.jnc.marketing.dto.response.product;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;

public class ProductResponse implements Serializable {

	private static final long serialVersionUID = 7867868178435087952L;

	public ProductResponse(Product product) {
		if (product == null)
			return;
		this.setId(product.getId());
		this.setName(product.getName());
		this.setDescription(product.getDescription());
		this.setOldId(product.getOldId());

		if (product.getProductType() == null)
			return;
		this.setTypeId(product.getProductType().getExternalId());
		this.setTypeName(product.getProductType().getName());

		if (product.getProductGroup() == null)
			return;
		this.setGroupId(product.getProductGroup().getId());
		this.setGroupName(product.getProductGroup().getName());

		String channels = "";
		for (Channel channel : product.getChannels()) {
			channels += channel.getName() + ",";
		}
		if (channels.lastIndexOf(",") >= 0) {
			channels = channels.substring(0, channels.length() - 1);
		}
		this.setChannels(channels);

		String dmsCategories = "";
		for (ProductDmsCategory dmsCategory : product.getProductDmsCategories()) {
			dmsCategories += dmsCategory.getName() + ",";
		}
		if (dmsCategories.lastIndexOf(",") >= 0) {
			dmsCategories = dmsCategories.substring(0, dmsCategories.length() - 1);
		}
		this.setDmsCategories(dmsCategories);
	}

	private String id;

	private String name;

	private String description;

	private String oldId;

	private String typeId;

	private String typeName;

	private String groupId;

	private String groupName;

	private String channels;

	private String dmsCategories;

	private String erpCategories;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public String getDmsCategories() {
		return dmsCategories;
	}

	public void setDmsCategories(String dmsCategories) {
		this.dmsCategories = dmsCategories;
	}

	public String getErpCategories() {
		return erpCategories;
	}

	public void setErpCategories(String erpCategories) {
		this.erpCategories = erpCategories;
	}

}

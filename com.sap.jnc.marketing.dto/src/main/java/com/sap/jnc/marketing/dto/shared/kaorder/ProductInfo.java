package com.sap.jnc.marketing.dto.shared.kaorder;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Product;

/**
 * @author Joel.Cheng I310645
 */
public class ProductInfo implements Serializable {

	private static final long serialVersionUID = -6207736066874489885L;

	private String id;

	private String name;

	private String description;

	public ProductInfo() {
	}

	public ProductInfo(Product product) {
		if (product == null) {
			return;
		}
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
	}

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

}

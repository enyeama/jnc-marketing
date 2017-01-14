package com.sap.jnc.marketing.dto.response.sparematerial;

import com.sap.jnc.marketing.persistence.model.Product;

/*
 * @author Kay, Du I326950
 */

public class SpareMaterialWechatResponse {
	private String id;
	private String title;
	private String value;
	
	public SpareMaterialWechatResponse(Product product) {
		this.setId(product.getId());
		this.setTitle(product.getName());
		this.setValue(product.getId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

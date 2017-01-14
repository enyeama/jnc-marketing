package com.sap.jnc.marketing.dto.response.product;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.ProductType;

public class ProductTypeResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String externalId;
	
	private String name;

	public ProductTypeResponse(ProductType productType) {
		this.setExternalId(productType.getExternalId());
		this.setName(productType.getName());
	}
	
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

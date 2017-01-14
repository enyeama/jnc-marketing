package com.sap.jnc.marketing.dto.response.product;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.ProductGroup;

public class ProductGroupResponse implements Serializable {

	private static final long serialVersionUID = -5069382201837756551L;

	public ProductGroupResponse(ProductGroup productGroup) {
		if (productGroup == null)
			return;
		this.setId(productGroup.getId());
		this.setName(productGroup.getName());
	}

	private String id;

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

package com.sap.jnc.marketing.dto.response.wechat.salesman;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Product;

public class SalesProductResponse implements Serializable {

	private static final long serialVersionUID = -5144545901208500265L;

	private String id;

	private String name;

	public SalesProductResponse(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
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
}

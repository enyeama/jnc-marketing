package com.sap.jnc.marketing.dto.request.wechat.salesman;

import java.io.Serializable;

public class SalesProductRequest implements Serializable {

	private static final long serialVersionUID = -5144545901208500265L;

	private String salesCategoryId;

	private String level4CategoryId;

	public String getSalesCategoryId() {
		return salesCategoryId;
	}

	public void setSalesCategoryId(String salesCategoryId) {
		this.salesCategoryId = salesCategoryId;
	}

	public String getLevel4CategoryId() {
		return level4CategoryId;
	}

	public void setLevel4CategoryId(String level4CategoryId) {
		this.level4CategoryId = level4CategoryId;
	}
}

package com.sap.jnc.marketing.persistence.criteria.impl;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

public class ProductSearchNode implements SearchKeywordNode {
	private static final long serialVersionUID = 1814141034699653939L;

	private String id;

	private String name;

	private String dmsCategoryId;

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

	public String getDmsCategoryId() {
		return dmsCategoryId;
	}

	public void setDmsCategoryId(String dmsCategoryId) {
		this.dmsCategoryId = dmsCategoryId;
	}

}

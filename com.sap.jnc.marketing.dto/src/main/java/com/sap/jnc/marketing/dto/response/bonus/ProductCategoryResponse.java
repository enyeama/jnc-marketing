package com.sap.jnc.marketing.dto.response.bonus;

import java.io.Serializable;

public class ProductCategoryResponse implements Serializable {

	private static final long serialVersionUID = 4575958057718453867L;

	private Long id;

	private String categoryId;
	private String categoryName;

	// private String level;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "ProductCategoryResponse [id=" + id + ", categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
	}

}

package com.sap.jnc.marketing.dto.response.product;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.ProductErpCategory;

public class ProductErpCategoryResponse implements Serializable {

	private static final long serialVersionUID = 5490500924473677249L;

	public ProductErpCategoryResponse(ProductErpCategory category) {
		if (category == null)
			return;
		this.setId(category.getId());
		this.setCategoryId(category.getCategoryId());
		this.setCategoryName(category.getCategoryName());
		this.setLevelName(category.getLevelName());
		this.setLevelNumber(category.getLevelNumber());
	}

	private Long id;

	private String levelNumber;

	private String levelName;

	private String categoryId;

	private String categoryName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(String levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
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

}

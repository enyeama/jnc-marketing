package com.sap.jnc.marketing.dto.response.product;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.ProductDmsCategoryLevel;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;

public class ProductDmsCategoryResponse implements Serializable {

	private static final long serialVersionUID = -4506190177129756461L;

	public ProductDmsCategoryResponse(ProductDmsCategory category) {
		if (category == null)
			return;
		this.setId(category.getId());
		this.setName(category.getName());
		this.setLevel(category.getLevel());

		if (category.getParentCategory() == null)
			return;
		this.setParentLevel(category.getParentCategory().getLevel());
		this.setParentName(category.getParentCategory().getName());
	}

	private String id;

	private ProductDmsCategoryLevel level;

	private String name;

	private ProductSalesCategory productSalesCategory;

	private ProductDmsCategoryLevel parentLevel;

	private String parentName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProductDmsCategoryLevel getLevel() {
		return level;
	}

	public void setLevel(ProductDmsCategoryLevel level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductSalesCategory getProductSalesCategory() {
		return productSalesCategory;
	}

	public void setProductSalesCategory(ProductSalesCategory productSalesCategory) {
		this.productSalesCategory = productSalesCategory;
	}

	public ProductDmsCategoryLevel getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(ProductDmsCategoryLevel parentLevel) {
		this.parentLevel = parentLevel;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getLabel() {
		return getName();
	}

	public String getValue() {
		return getId();
	}

}

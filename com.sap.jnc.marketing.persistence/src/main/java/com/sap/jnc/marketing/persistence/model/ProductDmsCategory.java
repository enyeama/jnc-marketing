package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.ProductDmsCategoryLevel;

@Entity
@Table(name = "T_PRODUCT_DMS_CATEGORY", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class ProductDmsCategory extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 7906009326413710154L;

	@Id
	@Column(name = "Id")
	private String id;

	@Column(name = "level")
	@Enumerated(EnumType.STRING)
	private ProductDmsCategoryLevel level;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salesCategoryId")
	private ProductSalesCategory productSalesCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private ProductDmsCategory parentCategory;

	@ManyToMany(mappedBy = "productDmsCategories")
	private List<Product> products;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProductDmsCategoryLevel getLevel() {
		return this.level;
	}

	public void setLevel(ProductDmsCategoryLevel level) {
		this.level = level;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductDmsCategory getParentCategory() {
		return this.parentCategory;
	}

	public void setParentCategory(ProductDmsCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ProductSalesCategory getProductSalesCategory() {
		return productSalesCategory;
	}

	public void setProductSalesCategory(ProductSalesCategory productSalesCategory) {
		this.productSalesCategory = productSalesCategory;
	}
}

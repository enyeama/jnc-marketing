package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_PRODUCT_ERP_CATEGORY", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "ProductErpCategorySeq", sequenceName = "SEQ_PRODUCTERPCATEGORY")
public class ProductErpCategory /*extends ModificatoryEntity*/ implements Serializable {

	private static final long serialVersionUID = 8303627229631458678L;

	public static final String PRODUCT_LEVEL_4_ID = "817";// "物料四级分类"代码

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProductErpCategorySeq")
	private Long id;

	@Column(name = "levelId")
	private String levelNumber;

	@Column(name = "levelName")
	private String levelName;

	@Column(name = "categoryId")
	private String categoryId;

	@Column(name = "categoryName")
	private String categoryName;

	@ManyToMany(mappedBy = "productErpCategories")
	private List<Product> products;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevelNumber() {
		return this.levelNumber;
	}

	public void setLevelNumber(String levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}

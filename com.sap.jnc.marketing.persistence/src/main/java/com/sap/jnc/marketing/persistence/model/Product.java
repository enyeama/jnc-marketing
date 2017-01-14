package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_PRODUCT", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class Product extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -6818670761359346784L;

	@Id
	@Column(name = "Id")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "oldId")
	private String oldId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productGroupId")
	private ProductGroup productGroup;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productTypeId")
	private ProductType productType;

	@OneToMany(mappedBy = "product")
	private List<IndividualProduct> individualProducts;

	@ManyToMany
	// Join Table
	@JoinTable(name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "PRODUCT_DMS_CATEGORY",
		// Columns - Self
		joinColumns = @JoinColumn(name = "productId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "dmsCategoryId"))
	private List<ProductDmsCategory> productDmsCategories;

	@ManyToMany
	@JoinTable(
		// Join Table
		name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "PRODUCT_ERP_CATEGORY",
		// Columns - Self
		joinColumns = @JoinColumn(name = "productId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "erpCategoryId"))
	private List<ProductErpCategory> productErpCategories;

	@ManyToMany
	@JoinTable(
		// Join Table
		name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "PRODUCT_CHANNEL",
		// Columns - Self
		joinColumns = @JoinColumn(name = "productId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "channelId"))
	private List<Channel> channels;

	@ManyToMany(mappedBy = "products")
	private List<PositionView> leaders;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public List<IndividualProduct> getIndividualProducts() {
		return individualProducts;
	}

	public void setIndividualProducts(List<IndividualProduct> individualProducts) {
		this.individualProducts = individualProducts;
	}

	public List<ProductDmsCategory> getProductDmsCategories() {
		return productDmsCategories;
	}

	public void setProductDmsCategories(List<ProductDmsCategory> productDmsCategories) {
		this.productDmsCategories = productDmsCategories;
	}

	public List<ProductErpCategory> getProductErpCategories() {
		return productErpCategories;
	}

	public void setProductErpCategories(List<ProductErpCategory> productErpCategories) {
		this.productErpCategories = productErpCategories;
	}

	public List<PositionView> getLeaders() {
		return leaders;
	}

	public void setLeaders(List<PositionView> leaders) {
		this.leaders = leaders;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
}

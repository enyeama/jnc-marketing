package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_PRODUCT_TYPE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class ProductType extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 2871519474738259019L;

	@Id
	@Column(name = "externalId")
	private String externalId;
	
	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "productType")
	private List<Product> products;

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}

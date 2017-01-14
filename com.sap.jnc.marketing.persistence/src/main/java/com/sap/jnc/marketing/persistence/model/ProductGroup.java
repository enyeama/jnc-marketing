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
@Table(name = "T_PRODUCT_GROUP", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class ProductGroup extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -5975807459712888853L;

	@Id
	@Column(name = "Id")
	private String id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "productGroup")
	private List<Product> products;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}

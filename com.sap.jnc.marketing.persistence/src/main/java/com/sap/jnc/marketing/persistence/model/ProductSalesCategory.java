package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_PRODUCT_SALES_CATEGORY", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "ProductSalesCategorySeq", sequenceName = "SEQ_PRODUCTSALESCATEGORY")
public class ProductSalesCategory implements Serializable {

	private static final long serialVersionUID = -1507835239513351155L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProductSalesCategorySeq")
	private Long id;

	@Column(name = "externalId", unique = true)
	private String externalId;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "productSalesCategory")
	private List<PositionView> positions;

	@OneToMany(mappedBy = "productSalesCategory")
	private List<EmployeeView> employees;

	@OneToMany(mappedBy = "productSalesCategory")
	private List<ProductDmsCategory> productDmsCategories;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PositionView> getPositions() {
		return this.positions;
	}

	public void setPositions(List<PositionView> positions) {
		this.positions = positions;
	}

	public List<ProductDmsCategory> getProductDmsCategories() {
		return productDmsCategories;
	}

	public void setProductDmsCategories(List<ProductDmsCategory> productDmsCategories) {
		this.productDmsCategories = productDmsCategories;
	}

	public List<EmployeeView> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeView> employees) {
		this.employees = employees;
	}
}

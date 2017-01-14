package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_EMPLOYEE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "EmployeeSeq", sequenceName = "SEQ_EMPLOYEE")
public class Employee extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 7343902321892864652L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EmployeeSeq")
	private Long id;

	@Column(name = "externalId")
	private String externalId;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "IdCardNO")
	private String idCardNO;
	
	@Column(name = "wechatAccountGroup")
	private String wechatAccountGroup;
	
	@Column(name = "loginAccountRole")
	private String loginAccountRole;

	private ValidityPeriod validityPeriod;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salesCategoryID")
	private ProductSalesCategory productSalesCategory;
//
//	@OneToMany(mappedBy = "employee")
//	private List<User> users;
//
//	@OneToMany(mappedBy = "provinceManager")
//	protected List<ProvinceManagerDepartment> offices;

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

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCardNO() {
		return this.idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public ProductSalesCategory getProductSalesCategory() {
		return productSalesCategory;
	}

	public void setProductSalesCategory(ProductSalesCategory productSalesCategory) {
		this.productSalesCategory = productSalesCategory;
	}

	public String getWechatAccountGroup() {
		return wechatAccountGroup;
	}

	public void setWechatAccountGroup(String wechatAccountGroup) {
		this.wechatAccountGroup = wechatAccountGroup;
	}

	public String getLoginAccountRole() {
		return loginAccountRole;
	}

	public void setLoginAccountRole(String loginAccountRole) {
		this.loginAccountRole = loginAccountRole;
	}
}

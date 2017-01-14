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

import org.apache.commons.collections4.CollectionUtils;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "V_EMPLOYEE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class EmployeeView implements Serializable {

	private static final long serialVersionUID = -7008756581601421222L;

	@Id
	@Column(name = "externalId")
	private String externalId;

	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "internalId")
	private Employee employee;

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

	@OneToMany(mappedBy = "employee")
	private List<UserReferenceToEmployee> userReferences;

	@ManyToMany
	@JoinTable(
		// Join Table
		name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "USER_EMPLOYEE",
		// Columns - Self
		joinColumns = @JoinColumn(name = "employeeId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "userId"))
	private List<User> users;

	@ManyToMany
	@JoinTable(
		// Join Table
		name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".V_" + "EMPLOYEE_POSITION",
		// Columns - Self
		joinColumns = @JoinColumn(name = "employeeExternalId", referencedColumnName = "externalId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "positionExternalId", referencedColumnName = "externalId"))
	private List<PositionView> positions;

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

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<PositionView> getPositions() {
		return this.positions;
	}

	public void setPositions(List<PositionView> positions) {
		this.positions = positions;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductSalesCategory getProductSalesCategory() {
		return this.productSalesCategory;
	}

	public void setProductSalesCategory(ProductSalesCategory productSalesCategory) {
		this.productSalesCategory = productSalesCategory;
	}

	public PositionView getPosition() {
		if (CollectionUtils.isEmpty(this.positions)) {
			return null;
		}
		return this.positions.get(0);
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getWechatAccountGroup() {
		return this.wechatAccountGroup;
	}

	public void setWechatAccountGroup(String wechatAccountGroup) {
		this.wechatAccountGroup = wechatAccountGroup;
	}

	public List<UserReferenceToEmployee> getUserReferences() {
		return this.userReferences;
	}

	public void setUserReferences(List<UserReferenceToEmployee> userReferences) {
		this.userReferences = userReferences;
	}

	public String getLoginAccountRole() {
		return loginAccountRole;
	}

	public void setLoginAccountRole(String loginAccountRole) {
		this.loginAccountRole = loginAccountRole;
	}
}

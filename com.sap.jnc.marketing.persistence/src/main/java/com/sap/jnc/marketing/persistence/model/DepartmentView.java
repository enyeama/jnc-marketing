package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "V_DEPARTMENT", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class DepartmentView implements Serializable {

	private static final long serialVersionUID = 6405303659835922193L;

	@Id
	@Column(name = "externalId")
	private String externalId;

	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "internalId")
	private Department department;

	@Column(name = "name")
	private String name;

	private ValidityPeriod validityPeriod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
			// Join Table
			name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".V_" + "DEPARTMENT_RELATION",
			// Columns - self
			joinColumns = @JoinColumn(name = "departmentId", referencedColumnName = "externalId"),
			// Columns - inverse
			inverseJoinColumns = @JoinColumn(name = "superiorDepartmentId", referencedColumnName = "externalId"))
	private DepartmentView superior;

	@OneToMany(mappedBy = "superior")
	private List<DepartmentView> subordinate;

	@OneToMany(mappedBy = "department")
	private List<PositionView> positions;

	@OneToMany(mappedBy = "office")
	private List<PositionView> provinceManagers;

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

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public List<PositionView> getPositions() {
		return this.positions;
	}

	public void setPositions(List<PositionView> positions) {
		this.positions = positions;
	}

	public DepartmentView getSuperior() {
		return this.superior;
	}

	public void setSuperior(DepartmentView superior) {
		this.superior = superior;
	}

	public List<DepartmentView> getSubordinate() {
		return this.subordinate;
	}

	public void setSubordinate(List<DepartmentView> subordinate) {
		this.subordinate = subordinate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PositionView> getProvinceManagers() {
		return this.provinceManagers;
	}

	public void setProvinceManagers(List<PositionView> provinceManagers) {
		this.provinceManagers = provinceManagers;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}

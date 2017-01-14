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
@Table(name = "T_DEPARTMENT", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "DepartmentSeq", sequenceName = "SEQ_DEPARTMENT")
public class Department extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -1550066860632979838L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DepartmentSeq")
	private Long id;

	@Column(name = "externalId")
	private String externalId;

	@Column(name = "name")
	private String name;

	private ValidityPeriod validityPeriod;

	@OneToMany(mappedBy = "office")
	private List<Audit> audits;

	@OneToMany
	protected List<DepartmentPositionAssignment> departmentPositionAssignments;

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

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public List<Audit> getAudits() {
		return this.audits;
	}

	public void setAudits(List<Audit> audits) {
		this.audits = audits;
	}

	public List<DepartmentPositionAssignment> getDepartmentPositionAssignments() {
		return departmentPositionAssignments;
	}

	public void setDepartmentPositionAssignments(List<DepartmentPositionAssignment> departmentPositionAssignments) {
		this.departmentPositionAssignments = departmentPositionAssignments;
	}
}

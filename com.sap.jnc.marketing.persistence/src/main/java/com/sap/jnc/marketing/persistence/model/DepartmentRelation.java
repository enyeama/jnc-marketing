package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "M_DEPARTMENT_RELATION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "DepartmentRelationSeq", sequenceName = "SEQ_DEPARTMENTRELATION")
public class DepartmentRelation extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 2520615816639421873L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DepartmentRelationSeq")
	private Long id;

	@Column(name = "departmentId")
	private String departmentId;

	@Column(name = "superiorDepartmentId")
	private String superiorDepartmentId;

	private ValidityPeriod validityPeriod;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getSuperiorDepartmentId() {
		return this.superiorDepartmentId;
	}

	public void setSuperiorDepartmentId(String superiorDepartmentId) {
		this.superiorDepartmentId = superiorDepartmentId;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
}

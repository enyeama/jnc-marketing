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

/**
 * 这个中间表是PO强烈要求建的，如果出现任何关系维护出错，不管我的事哈，我已经强烈建议不要暴露任何中间表了。
 *
 * @author I071053 Diouf Du
 */
@Entity
@Table(name = "M_DEPARTMENT_POSITION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "DepartmentPositionAssignmentSeq", sequenceName = "SEQ_DEPARTMENTPOSITIONASSIGNMENT")
public class DepartmentPositionAssignment extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -1005016469045693332L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DepartmentPositionAssignmentSeq")
	private Long id;

	@Column(name = "departmentExternalId")
	private String departmentExternalId;

	@Column(name = "positionExternalId")
	private String positionExternalId;

	private ValidityPeriod validityPeriod;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getDepartmentExternalId() {
		return this.departmentExternalId;
	}

	public void setDepartmentExternalId(String departmentExternalId) {
		this.departmentExternalId = departmentExternalId;
	}

	public String getPositionExternalId() {
		return this.positionExternalId;
	}

	public void setPositionExternalId(String positionExternalId) {
		this.positionExternalId = positionExternalId;
	}
}

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
 * @author I071053 Diouf Du
 */
@Entity
@Table(name = "M_EMPLOYEE_POSITION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "EmployeePositionAssignmentSeq", sequenceName = "SEQ_EMPLOYEEPOSITIONASSIGNMENT")
public class EmployeePositionAssignment extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -1005016469045693332L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EmployeePositionAssignmentSeq")
	private Long id;

	@Column(name = "employeeExternalId")
	private String employeeExternalId;

	@Column(name = "positionExternalId")
	private String positionExternalId;

	private ValidityPeriod validityPeriod;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeExternalId() {
		return this.employeeExternalId;
	}

	public void setEmployeeExternalId(String employeeExternalId) {
		this.employeeExternalId = employeeExternalId;
	}

	public String getPositionExternalId() {
		return this.positionExternalId;
	}

	public void setPositionExternalId(String positionExternalId) {
		this.positionExternalId = positionExternalId;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

}

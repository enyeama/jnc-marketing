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
@Table(name = "M_JOB_POSITION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "JobPositionAssignmentSeq", sequenceName = "SEQ_JOBPOSITIONASSIGNMENT")
public class JobPositionAssignment extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -4795515436637854814L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JobPositionAssignmentSeq")
	private Long id;

	@Column(name = "jobExternalId")
	private String jobExternalId;

	@Column(name = "positionExternalId")
	private String positionExternalId;

	private ValidityPeriod validityPeriod;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobExternalId() {
		return this.jobExternalId;
	}

	public void setJobExternalId(String jobExternalId) {
		this.jobExternalId = jobExternalId;
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

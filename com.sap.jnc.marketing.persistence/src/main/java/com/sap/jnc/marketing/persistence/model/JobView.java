package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

/**
 * 职务表
 */
@Entity
@Table(name = "V_JOB", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class JobView implements Serializable {

	private static final long serialVersionUID = 3506679845514686669L;

	@Id
	@Column(name = "externalId")
	private String externalId;

	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "internalId")
	private Job job;

	@Column(name = "name")
	private String name;

	@OneToOne(mappedBy = "job")
	private PositionView position;

	private ValidityPeriod validityPeriod;

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

	public PositionView getPosition() {
		return this.position;
	}

	public void setPosition(PositionView position) {
		this.position = position;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
}

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
@Table(name = "M_REGION_POSITION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "RegionPositionAssignmentSeq", sequenceName = "SEQ_REGIONPOSITIONASSIGNMENT")
public class RegionPositionAssignment extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 1451858611152134818L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RegionPositionAssignmentSeq")
	private Long id;

	@Column(name = "regionId")
	private String regionId;

	@Column(name = "positionExternalId")
	private String positionExternalId;

	private ValidityPeriod validityPeriod;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegionId() {
		return this.regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
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

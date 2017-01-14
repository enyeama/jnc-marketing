package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_CONTRACT_ITEM", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "ContractItemSeq", sequenceName = "SEQ_CONTRACTITEM")
public class ContractItem extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 7645790476413969047L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ContractItemSeq")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelId")
	private Channel channel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dmsCategoryId")
	private ProductDmsCategory dmsCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contractId")
	private Contract contract;

	@ManyToMany
	@JoinTable(
		// Join Table
		name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "CONTRACT_ITEM_REGION",
		// Columns - Self
		joinColumns = @JoinColumn(name = "contractItemId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "regionId"))
	private List<Region> regions;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ProductDmsCategory getDmsCategory() {
		return this.dmsCategory;
	}

	public void setDmsCategory(ProductDmsCategory dmsCategory) {
		this.dmsCategory = dmsCategory;
	}

	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public List<Region> getRegions() {
		return this.regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
}

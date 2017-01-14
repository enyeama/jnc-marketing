package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.enumeration.RebateTargetType;

/**
 * Author: C5205383 Kevin.Ren
 */
@Cacheable(false)
@Entity
@Table(name = "T_BANQUET_REBATE_CONFIG", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BanquetRebateConfigSeq", sequenceName = "SEQ_BANQUETREBATECONFIG")
public class BanquetRebateConfig extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -3567398478820145569L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BanquetRebateConfigSeq")
	private Long id;

	@Column(name = "applicationType")
	@Enumerated(EnumType.STRING)
	private BanquetApplicationType applicationType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelId")
	private Channel channel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;

	@Column(name = "rebateTargetType")
	@Enumerated(EnumType.STRING)
	private RebateTargetType rebateTargetType;

	private Amount amount;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RebateTargetType getRebateTargetType() {
		return this.rebateTargetType;
	}

	public void setRebateTargetType(RebateTargetType rebateTargetType) {
		this.rebateTargetType = rebateTargetType;
	}

	public Amount getAmount() {
		return this.amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public BanquetApplicationType getApplicationType() {
		return this.applicationType;
	}

	public void setApplicationType(BanquetApplicationType applicationType) {
		this.applicationType = applicationType;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}

package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

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
import com.sap.jnc.marketing.persistence.enumeration.RebateTargetType;

/**
 * Author: C5205383 Kevin.Ren
 */
@Cacheable(false)
@Entity
@Table(name = "T_BANQUET_REBATE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BanquetRebateSeq", sequenceName = "SEQ_BANQUETREBATE")
public class BanquetRebate extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -4946715493216734646L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BanquetRebateSeq")
	private Long id;

	@Column(name = "externalId")
	private String externalId;

	@Column(name = "batch")
	private String batch;

	@Column(name = "rebateTargetType")
	@Enumerated(EnumType.STRING)
	private RebateTargetType rebateTargetType;

	@Column(name = "rebateObjectId")
	private String rebateObjectId;

	@Column(name = "rebateTime")
	private Calendar rebateTime;

	@Column(name = "wechatBonusNumber")
	private String wechatBonusNumber;

	private Amount amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetId")
	private Banquet banquet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "individualProductId")
	private IndividualProduct individualProduct;

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

	public String getBatch() {
		return this.batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public RebateTargetType getRebateTargetType() {
		return this.rebateTargetType;
	}

	public void setRebateTargetType(RebateTargetType rebateTargetType) {
		this.rebateTargetType = rebateTargetType;
	}

	public RebateTargetType getRebateObjectType() {
		return this.rebateTargetType;
	}

	public void setRebateObjectType(RebateTargetType rebateTargetType) {
		this.rebateTargetType = rebateTargetType;
	}

	public String getRebateObjectId() {
		return this.rebateObjectId;
	}

	public void setRebateObjectId(String rebateObjectId) {
		this.rebateObjectId = rebateObjectId;
	}

	public Calendar getRebateTime() {
		return this.rebateTime;
	}

	public void setRebateTime(Calendar rebateTime) {
		this.rebateTime = rebateTime;
	}

	public Amount getAmount() {
		return this.amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public Banquet getBanquet() {
		return this.banquet;
	}

	public void setBanquet(Banquet banquet) {
		this.banquet = banquet;
	}

	public IndividualProduct getIndividualProduct() {
		return this.individualProduct;
	}

	public void setIndividualProduct(IndividualProduct individualProduct) {
		this.individualProduct = individualProduct;
	}

	public String getWechatBonusNumber() {
		return this.wechatBonusNumber;
	}

	public void setWechatBonusNumber(String wechatBonusNumber) {
		this.wechatBonusNumber = wechatBonusNumber;
	}
}

package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "T_INDIVIDUAL_PRODUCT_REBATE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "IndividualProductRebateSeq", sequenceName = "SEQ_INDIVIDUALPRODUCTREBATE")
public class IndividualProductRebate extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 1602868637078021215L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IndividualProductRebateSeq")
	private Long id;

	@Column(name = "year")
	private String year;

	@Column(name = "month")
	private String month;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "capInnerCode")
	private IndividualProduct individualProduct;

	@Column(name = "rebateTargetType")
	private RebateTargetType rebateTargetType;

	@Column(name = "rebateTargetId")
	private String rebateTargetId;

	private Amount amount;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public IndividualProduct getiMaterialMaster() {
		return this.individualProduct;
	}

	public void setiMaterialMaster(IndividualProduct individualProduct) {
		this.individualProduct = individualProduct;
	}

	public IndividualProduct getIndividualProduct() {
		return this.individualProduct;
	}

	public void setIndividualProduct(IndividualProduct individualProduct) {
		this.individualProduct = individualProduct;
	}

	public RebateTargetType getRebateTargetType() {
		return this.rebateTargetType;
	}

	public void setRebateTargetType(RebateTargetType rebateTargetType) {
		this.rebateTargetType = rebateTargetType;
	}

	public String getRebateTargetId() {
		return this.rebateTargetId;
	}

	public void setRebateTargetId(String rebateTargetId) {
		this.rebateTargetId = rebateTargetId;
	}

	public Amount getAmount() {
		return this.amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

}

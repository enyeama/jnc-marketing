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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.BanquetItemVerificationStatus;

/**
 * Author: C5205383 Kevin.Ren
 */
@Cacheable(false)
@Entity
@Table(name = "T_BANQUET_ITEM", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BanquetItemSeq", sequenceName = "SEQ_BANQUETITEM")
public class BanquetItem extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 6161396321703763863L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BanquetItemSeq")
	private Long id;

	@Column(name = "capOuterCode")
	private String capOuterCode;

	@Column(name = "capInnerCode")
	private String capInnerCode;

	@Column(name = "boxId")
	private String boxId;

	@Column(name = "caseId")
	private String caseId;

	@Column(name = "producationBatchCode")
	private String producationBatchCode;

	@Column(name = "isPaid")
	private Boolean isPaid;

	@Column(name = "verifStatus")
	@Enumerated(EnumType.STRING)
	private BanquetItemVerificationStatus verifStatus;

	@OneToOne
	@JoinColumn(name = "individualProductId")
	private IndividualProduct individualProduct;

	@OneToOne
	@JoinColumn(name = "oldIndividualProductId")
	private OldIndividualProduct oldIndividualProduct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetId")
	private Banquet banquet;

	public String getCapOuterCode() {
		return this.capOuterCode;
	}

	public void setCapOuterCode(String capOuterCode) {
		this.capOuterCode = capOuterCode;
	}

	public String getBoxId() {
		return this.boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getProducationBatchCode() {
		return this.producationBatchCode;
	}

	public void setProducationBatchCode(String producationBatchCode) {
		this.producationBatchCode = producationBatchCode;
	}

	public Boolean getIsPaid() {
		return this.isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public BanquetItemVerificationStatus getVerifStatus() {
		return this.verifStatus;
	}

	public void setVerifStatus(BanquetItemVerificationStatus verifStatus) {
		this.verifStatus = verifStatus;
	}

	public Banquet getBanquet() {
		return this.banquet;
	}

	public void setBanquet(Banquet banquet) {
		this.banquet = banquet;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IndividualProduct getIndividualProduct() {
		return this.individualProduct;
	}

	public void setIndividualProduct(IndividualProduct individualProduct) {
		this.individualProduct = individualProduct;
	}

	public OldIndividualProduct getOldIndividualProduct() {
		return this.oldIndividualProduct;
	}

	public void setOldIndividualProduct(OldIndividualProduct oldIndividualProduct) {
		this.oldIndividualProduct = oldIndividualProduct;
	}

	public String getCapInnerCode() {
		return this.capInnerCode;
	}

	public void setCapInnerCode(String capInnerCode) {
		this.capInnerCode = capInnerCode;
	}
}

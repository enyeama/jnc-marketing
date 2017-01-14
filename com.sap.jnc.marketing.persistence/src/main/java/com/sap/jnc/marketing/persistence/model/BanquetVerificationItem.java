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
import com.sap.jnc.marketing.persistence.enumeration.BanquetScanType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerificationInvalidReason;
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerificationResult;

/**
 * @author C5205383 Kevin Ren
 */
@Cacheable(false)
@Entity
@Table(name = "T_BANQUET_VERIFICATION_ITEM", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BanquetVerificationItemSeq", sequenceName = "SEQ_BANQUETVERIFICATIONITEM")
public class BanquetVerificationItem extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 8548463135722020854L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BanquetVerificationItemSeq")
	private Long id;

	// 回收单
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetVerificationId")
	private BanquetVerification banquetVerification;

	// 扫码类型
	@Column(name = "scanType")
	@Enumerated(EnumType.STRING)
	private BanquetScanType scanType;

	// 报备经销商
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetDealerId")
	private Dealer banquetDealer;

	@Column(name = "capOuterCode")
	private String capOuterCode;

	@Column(name = "caseId")
	private String caseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "individualProductId")
	private IndividualProduct individualProduct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oldIndividualProductId")
	private OldIndividualProduct oldIndividualProduct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;

	// 实际报备单号
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "originalBanquetId")
	private Banquet originalBanquet;

	// 物料经销商
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productDealerId")
	private Dealer productDealer;

	// 拉环结果
	@Column(name = "result")
	@Enumerated(EnumType.STRING)
	private BanquetVerificationResult result;

	// 不合格原因
	@Column(name = "invalidReason")
	@Enumerated(EnumType.STRING)
	private BanquetVerificationInvalidReason invalidReason;

	// 录码人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codeRecorder")
	private Employee codeRecorder;

	// 录码提交时间
	@Column(name = "commitTimeInRecordCode")
	private Calendar commitTimeInRecordCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BanquetVerification getBanquetVerification() {
		return banquetVerification;
	}

	public void setBanquetVerification(BanquetVerification banquetVerification) {
		this.banquetVerification = banquetVerification;
	}

	public BanquetScanType getScanType() {
		return scanType;
	}

	public void setScanType(BanquetScanType scanType) {
		this.scanType = scanType;
	}

	public Dealer getBanquetDealer() {
		return banquetDealer;
	}

	public void setBanquetDealer(Dealer banquetDealer) {
		this.banquetDealer = banquetDealer;
	}

	public String getCapOuterCode() {
		return capOuterCode;
	}

	public void setCapOuterCode(String capOuterCode) {
		this.capOuterCode = capOuterCode;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public IndividualProduct getIndividualProduct() {
		return individualProduct;
	}

	public void setIndividualProduct(IndividualProduct individualProduct) {
		this.individualProduct = individualProduct;
	}

	public OldIndividualProduct getOldIndividualProduct() {
		return oldIndividualProduct;
	}

	public void setOldIndividualProduct(OldIndividualProduct oldIndividualProduct) {
		this.oldIndividualProduct = oldIndividualProduct;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Banquet getOriginalBanquet() {
		return originalBanquet;
	}

	public void setOriginalBanquet(Banquet originalBanquet) {
		this.originalBanquet = originalBanquet;
	}

	public Dealer getProductDealer() {
		return productDealer;
	}

	public void setProductDealer(Dealer productDealer) {
		this.productDealer = productDealer;
	}

	public BanquetVerificationResult getResult() {
		return result;
	}

	public void setResult(BanquetVerificationResult result) {
		this.result = result;
	}

	public BanquetVerificationInvalidReason getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(BanquetVerificationInvalidReason invalidReason) {
		this.invalidReason = invalidReason;
	}

	public Employee getCodeRecorder() {
		return codeRecorder;
	}

	public void setCodeRecorder(Employee codeRecorder) {
		this.codeRecorder = codeRecorder;
	}

	public Calendar getCommitTimeInRecordCode() {
		return commitTimeInRecordCode;
	}

	public void setCommitTimeInRecordCode(Calendar commitTimeInRecordCode) {
		this.commitTimeInRecordCode = commitTimeInRecordCode;
	}
}

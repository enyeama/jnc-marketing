package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.collections4.CollectionUtils;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.BanquetPictureVerifyStatus;
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerifyApplicationStatus;

/**
 * @author C5205383 Kevin Ren
 */
@Cacheable(false)
@Entity
@Table(name = "T_BANQUET_VERIFY_APPLICATION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BanquetVerifyApplicationSeq", sequenceName = "SEQ_BANQUETVERIFYAPPLICATION")
public class BanquetVerifyApplication extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -2051184746383374512L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BanquetVerifyApplicationSeq")
	private Long id;

	@Column(name = "expressNO")
	private String expressNO;

	@Column(name = "expressTime")
	private Calendar expressTime;

	@Column(name = "planArriveTime")
	private Calendar planArriveTime;

	@Column(name = "applyTime")
	private Calendar applyTime;

	@Column(name = "expressCompany")
	private String expressCompany;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private BanquetVerifyApplicationStatus status;

	@Column(name = "verifier")
	private String verifier;

	@Column(name = "verifiedFinishTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar verifiedFinishTime;

	private BottleQuantity bottleQuantity;

	private VerifiedBottleQuantity verifiedBottleQuantity;

	@Column(name = "boxQuantity")
	private Integer boxQuantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatorId")
	private Employee creator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officeId")
	private Department office;

	@Column(name = "salesChannel")
	private String salesChannel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetId")
	private Banquet banquet;

	// 照片审核人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pictureVerifierId")
	private Employee pictureVerifier;

	// 照片审核时间
	@Column(name = "pictureVerifyTime")
	private Calendar pictureVerifyTime;

	// 照片审核状态
	@Column(name = "pictureVerifyStatus")
	@Enumerated(EnumType.STRING)
	private BanquetPictureVerifyStatus pictureVerifyStatus;

	// 照片审核结果
	@Column(name = "pictureVerifyResult")
	private String pictureVerifyResult;

	// 照片审核备注
	@Column(name = "pictureVerifyComment")
	private String pictureVerifyComment;

	// 拉环回收单
	@OneToMany(mappedBy = "banquetVerifyApplication")
	private List<BanquetVerification> banquetVerifications;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpressNO() {
		return this.expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public Calendar getExpressTime() {
		return this.expressTime;
	}

	public void setExpressTime(Calendar expressTime) {
		this.expressTime = expressTime;
	}

	public Calendar getPlanArriveTime() {
		return this.planArriveTime;
	}

	public void setPlanArriveTime(Calendar planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public Calendar getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Calendar applyTime) {
		this.applyTime = applyTime;
	}

	public String getExpressCompany() {
		return this.expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public BanquetVerifyApplicationStatus getStatus() {
		return this.status;
	}

	public void setStatus(BanquetVerifyApplicationStatus status) {
		this.status = status;
	}

	public String getVerifier() {
		return this.verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public Calendar getVerifiedFinishTime() {
		return this.verifiedFinishTime;
	}

	public void setVerifiedFinishTime(Calendar verifiedFinishTime) {
		this.verifiedFinishTime = verifiedFinishTime;
	}

	public BottleQuantity getBottleQuantity() {
		return this.bottleQuantity;
	}

	public void setBottleQuantity(BottleQuantity bottleQuantity) {
		this.bottleQuantity = bottleQuantity;
	}

	public VerifiedBottleQuantity getVerifiedBottleQuantity() {
		return this.verifiedBottleQuantity;
	}

	public void setVerifiedBottleQuantity(VerifiedBottleQuantity verifiedBottleQuantity) {
		this.verifiedBottleQuantity = verifiedBottleQuantity;
	}

	public Employee getCreator() {
		return this.creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

	public Department getOffice() {
		return this.office;
	}

	public void setOffice(Department office) {
		this.office = office;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public Banquet getBanquet() {
		return this.banquet;
	}

	public void setBanquet(Banquet banquet) {
		this.banquet = banquet;
	}

	public Employee getPictureVerifier() {
		return pictureVerifier;
	}

	public void setPictureVerifier(Employee pictureVerifier) {
		this.pictureVerifier = pictureVerifier;
	}

	public Calendar getPictureVerifyTime() {
		return pictureVerifyTime;
	}

	public void setPictureVerifyTime(Calendar pictureVerifyTime) {
		this.pictureVerifyTime = pictureVerifyTime;
	}

	public BanquetPictureVerifyStatus getPictureVerifyStatus() {
		return pictureVerifyStatus;
	}

	public void setPictureVerifyStatus(BanquetPictureVerifyStatus pictureVerifyStatus) {
		this.pictureVerifyStatus = pictureVerifyStatus;
	}

	public String getPictureVerifyResult() {
		return pictureVerifyResult;
	}

	public void setPictureVerifyResult(String pictureVerifyResult) {
		this.pictureVerifyResult = pictureVerifyResult;
	}

	public String getPictureVerifyComment() {
		return pictureVerifyComment;
	}

	public void setPictureVerifyComment(String pictureVerifyComment) {
		this.pictureVerifyComment = pictureVerifyComment;
	}

	public Integer getBoxQuantity() {
		return boxQuantity;
	}

	public void setBoxQuantity(Integer boxQuantity) {
		this.boxQuantity = boxQuantity;
	}

	public BanquetVerification getBanquetVerification() {
		if (CollectionUtils.isEmpty(this.banquetVerifications)) {
			return null;
		}
		return this.banquetVerifications.get(0);
	}

	public void setBanquetVerification(BanquetVerification banquetVerification) {
		if (CollectionUtils.isEmpty(this.banquetVerifications)) {
			this.banquetVerifications = new ArrayList<>(1);
			this.banquetVerifications.add(banquetVerification);
		}
		else {
			this.banquetVerifications.set(0, banquetVerification);
		}
	}
}

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
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerifyApplicationStatus;

/**
 * @author C5205383 Kevin Ren
 */
@Cacheable(false)
@Entity
@Table(name = "T_BANQUET_VERIFICATION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BanquetVerificationSeq", sequenceName = "SEQ_BANQUETVERIFICATION")
public class BanquetVerification extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -7634536612811294035L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BanquetVerificationSeq")
	private Long id;

	// 回收单号 日期+人员首字母（签收操作人员）+4位序号
	@Column(name = "verificationNumber")
	private String verificationNumber;

	// 流程状态
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private BanquetVerifyApplicationStatus status;

	// 宴会报备单
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetId")
	private Banquet banquet;

	// 宴会核销申请
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetVerifyApplicationId")
	private BanquetVerifyApplication banquetVerifyApplication;

	// 初次核对瓶盖数
	@Column(name = "capQuantityOfFirstVerification")
	private Integer capQuantityOfFirstVerification;

	// 初次核对母扣数
	@Column(name = "exteriorRingQuantityOfFirstVerification")
	private Integer exteriorRingQuantityOfFirstVerification;

	// 初次核对子扣数
	@Column(name = "interiorRingQuantityOfFirstVerification")
	private Integer interiorRingQuantityOfFirstVerification;

	// 初次核对判断人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verifierOfFirstVerification")
	private Employee verifierOfFirstVerification;

	// 初次核对时间
	@Column(name = "timeOfFirstVerification")
	private Calendar timeOfFirstVerification;

	// 初次核对备注
	@Column(name = "commentOfFirstVerification")
	private String commentOfFirstVerification;

	// 初次核对保存时间
	@Column(name = "savingTimeOfFirstVerification")
	private Calendar savingTimeOfFirstVerification;

	// 初次核对保存人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saverimeOfFirstVerification")
	private Employee saverTimeOfFirstVerification;

	// 录码合格母扣数
	@Column(name = "validExteriortRingQuantityInRecordCode")
	private Integer validExteriortRingQuantityInRecordCode;

	// 录码母扣常规不合格数
	@Column(name = "conventionalInvalidQuantityOfExteriorRingInRecordCode")
	private Integer conventionalInvalidQuantityOfExteriorRingInRecordCode;

	// 录码残码数
	@Column(name = "incompleteQuantityOfRecordCode")
	private Integer incompleteQuantityOfRecordCode;

	// 录码未报备数
	@Column(name = "notReportedQuantityInRecordCode")
	private Integer notReportedQuantityInRecordCode;

	// 录码未兑付数
	@Column(name = "unpaidQuantityInRecordCode")
	private Integer unpaidQuantityInRecordCode;

	// 录码非兑付版本数
	@Column(name = "invalidPaymentVersionQuantityInRecordCode")
	private Integer invalidPaymentVersionQuantityInRecordCode;

	// 录码非本报备单号数
	@Column(name = "invalidReportNumberQuantityInRecordCode")
	private Integer invalidReportNumberQuantityInRecordCode;

	// 录码非常规不合格数
	@Column(name = "unconventionalInvalidQuantityInRecordCode")
	private Integer unconventionalInvalidQuantityInRecordCode;

	// 录码非常规不合格原因
	@Column(name = "unconventionalInvalidReasonInRecordCode")
	private String unconventionalInvalidReasonInRecordCode;

	// 扫码合格母扣数
	@Column(name = "validExteriorRingPullQuantityInScanCode")
	private Integer validExteriorRingPullQuantityInScanCode;

	// 扫码母扣常规不合格数
	@Column(name = "conventionalInvalidQuantityInScanCode")
	private Integer conventionalInvalidQuantityInScanCode;

	// 扫码未报备数
	@Column(name = "notReportedQuantityInScanCode")
	private Integer notReportedQuantityInScanCode;

	// 扫码未兑付数
	@Column(name = "unpaidQuantityInScanCode")
	private Integer unpaidQuantityInScanCode;

	// 扫码非兑付版本数
	@Column(name = "invalidPaymentVersionQuantityInScanCode")
	private Integer invalidPaymentVersionQuantityInScanCode;

	// 扫码非本报备单号数
	@Column(name = "invalidReportNumberQuantityInScanCode")
	private Integer invalidReportNumberQuantityInScanCode;

	// 扫码非常规不合格数
	@Column(name = "unconventionalInvalidQuantityInScanCode")
	private Integer unconventionalInvalidQuantityInScanCode;

	// 扫码非常规不合格原因
	@Column(name = "unconventionalInvalidReasonInScanCode")
	private String unconventionalInvalidReasonInScanCode;

	// 开始扫码时间
	@Column(name = "startTimeOfFirstVerification")
	private Calendar startTimeOfFirstVerification;

	// 保存时间
	@Column(name = "saveTime")
	private Calendar saveTime;

	// 结束扫码时间
	@Column(name = "endTimeOfFirstVerification")
	private Calendar endTimeOfFirstVerification;

	// 录码人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codeRecorder")
	private Employee codeRecorder;

	// 录码提交时间
	@Column(name = "commitTimeInRecordCode")
	private Calendar commitTimeInRecordCode;

	// 复核合格母扣数
	@Column(name = "validExteriorRingQuantityInRecheck")
	private Integer validExteriorRingQuantityInRecheck;

	// 复核母扣不合格数
	@Column(name = "invalidExteriorRingQuantityInRecheck")
	private Integer invalidExteriorRingQuantityInRecheck;

	// 复核合格瓶盖数
	@Column(name = "validCapQuantityInRecheck")
	private Integer validCapQuantityInRecheck;

	// 复核瓶盖不合格数
	@Column(name = "invalidCapQuantityInRecheck")
	private Integer invalidCapQuantityInRecheck;

	// 最终合格套数
	@Column(name = "validQuantity")
	private Integer validQuantity;

	// 最终不合格套数
	@Column(name = "invalidQuantity")
	private Integer invalidQuantity;

	// 母扣非常规不合格原因
	@Column(name = "unconventionalInvalidReasonOfExteriorRing")
	private String unconventionalInvalidReasonOfExteriorRing;

	// 瓶盖非常规不合格原因
	@Column(name = "unconventionalInvalidReasonOfCap")
	private String unconventionalInvalidReasonOfCap;

	// 复核人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recheckerId")
	private Employee rechecker;

	// 复核时间
	@Column(name = "reckeckTime")
	private Calendar reckeckTime;

	// 拉环转核销人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toVerifyPersonId")
	private Employee toVerifyPerson;

	// 拉环转核销时间
	@Column(name = "toVerifyTime")
	private Calendar toVerifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVerificationNumber() {
		return verificationNumber;
	}

	public void setVerificationNumber(String verificationNumber) {
		this.verificationNumber = verificationNumber;
	}

	public BanquetVerifyApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(BanquetVerifyApplicationStatus status) {
		this.status = status;
	}

	public Banquet getBanquet() {
		return banquet;
	}

	public void setBanquet(Banquet banquet) {
		this.banquet = banquet;
	}

	public BanquetVerifyApplication getBanquetVerifyApplication() {
		return banquetVerifyApplication;
	}

	public void setBanquetVerifyApplication(BanquetVerifyApplication banquetVerifyApplication) {
		this.banquetVerifyApplication = banquetVerifyApplication;
	}

	public Integer getCapQuantityOfFirstVerification() {
		return capQuantityOfFirstVerification;
	}

	public void setCapQuantityOfFirstVerification(Integer capQuantityOfFirstVerification) {
		this.capQuantityOfFirstVerification = capQuantityOfFirstVerification;
	}

	public Integer getExteriorRingQuantityOfFirstVerification() {
		return exteriorRingQuantityOfFirstVerification;
	}

	public void setExteriorRingQuantityOfFirstVerification(Integer exteriorRingQuantityOfFirstVerification) {
		this.exteriorRingQuantityOfFirstVerification = exteriorRingQuantityOfFirstVerification;
	}

	public Integer getInteriorRingQuantityOfFirstVerification() {
		return interiorRingQuantityOfFirstVerification;
	}

	public void setInteriorRingQuantityOfFirstVerification(Integer interiorRingQuantityOfFirstVerification) {
		this.interiorRingQuantityOfFirstVerification = interiorRingQuantityOfFirstVerification;
	}

	public Employee getVerifierOfFirstVerification() {
		return verifierOfFirstVerification;
	}

	public void setVerifierOfFirstVerification(Employee verifierOfFirstVerification) {
		this.verifierOfFirstVerification = verifierOfFirstVerification;
	}

	public Calendar getTimeOfFirstVerification() {
		return timeOfFirstVerification;
	}

	public void setTimeOfFirstVerification(Calendar timeOfFirstVerification) {
		this.timeOfFirstVerification = timeOfFirstVerification;
	}

	public String getCommentOfFirstVerification() {
		return commentOfFirstVerification;
	}

	public void setCommentOfFirstVerification(String commentOfFirstVerification) {
		this.commentOfFirstVerification = commentOfFirstVerification;
	}

	public Calendar getSavingTimeOfFirstVerification() {
		return savingTimeOfFirstVerification;
	}

	public void setSavingTimeOfFirstVerification(Calendar savingTimeOfFirstVerification) {
		this.savingTimeOfFirstVerification = savingTimeOfFirstVerification;
	}

	public Employee getSaverTimeOfFirstVerification() {
		return saverTimeOfFirstVerification;
	}

	public void setSaverTimeOfFirstVerification(Employee saverTimeOfFirstVerification) {
		this.saverTimeOfFirstVerification = saverTimeOfFirstVerification;
	}

	public Integer getValidExteriortRingQuantityInRecordCode() {
		return validExteriortRingQuantityInRecordCode;
	}

	public void setValidExteriortRingQuantityInRecordCode(Integer validExteriortRingQuantityInRecordCode) {
		this.validExteriortRingQuantityInRecordCode = validExteriortRingQuantityInRecordCode;
	}

	public Integer getConventionalInvalidQuantityOfExteriorRingInRecordCode() {
		return conventionalInvalidQuantityOfExteriorRingInRecordCode;
	}

	public void setConventionalInvalidQuantityOfExteriorRingInRecordCode(Integer conventionalInvalidQuantityOfExteriorRingInRecordCode) {
		this.conventionalInvalidQuantityOfExteriorRingInRecordCode = conventionalInvalidQuantityOfExteriorRingInRecordCode;
	}

	public Integer getIncompleteQuantityOfRecordCode() {
		return incompleteQuantityOfRecordCode;
	}

	public void setIncompleteQuantityOfRecordCode(Integer incompleteQuantityOfRecordCode) {
		this.incompleteQuantityOfRecordCode = incompleteQuantityOfRecordCode;
	}

	public Integer getNotReportedQuantityInRecordCode() {
		return notReportedQuantityInRecordCode;
	}

	public void setNotReportedQuantityInRecordCode(Integer notReportedQuantityInRecordCode) {
		this.notReportedQuantityInRecordCode = notReportedQuantityInRecordCode;
	}

	public Integer getUnpaidQuantityInRecordCode() {
		return unpaidQuantityInRecordCode;
	}

	public void setUnpaidQuantityInRecordCode(Integer unpaidQuantityInRecordCode) {
		this.unpaidQuantityInRecordCode = unpaidQuantityInRecordCode;
	}

	public Integer getInvalidPaymentVersionQuantityInRecordCode() {
		return invalidPaymentVersionQuantityInRecordCode;
	}

	public void setInvalidPaymentVersionQuantityInRecordCode(Integer invalidPaymentVersionQuantityInRecordCode) {
		this.invalidPaymentVersionQuantityInRecordCode = invalidPaymentVersionQuantityInRecordCode;
	}

	public Integer getInvalidReportNumberQuantityInRecordCode() {
		return invalidReportNumberQuantityInRecordCode;
	}

	public void setInvalidReportNumberQuantityInRecordCode(Integer invalidReportNumberQuantityInRecordCode) {
		this.invalidReportNumberQuantityInRecordCode = invalidReportNumberQuantityInRecordCode;
	}

	public Integer getUnconventionalInvalidQuantityInRecordCode() {
		return unconventionalInvalidQuantityInRecordCode;
	}

	public void setUnconventionalInvalidQuantityInRecordCode(Integer unconventionalInvalidQuantityInRecordCode) {
		this.unconventionalInvalidQuantityInRecordCode = unconventionalInvalidQuantityInRecordCode;
	}

	public String getUnconventionalInvalidReasonInRecordCode() {
		return unconventionalInvalidReasonInRecordCode;
	}

	public void setUnconventionalInvalidReasonInRecordCode(String unconventionalInvalidReasonInRecordCode) {
		this.unconventionalInvalidReasonInRecordCode = unconventionalInvalidReasonInRecordCode;
	}

	public Integer getValidExteriorRingPullQuantityInScanCode() {
		return validExteriorRingPullQuantityInScanCode;
	}

	public void setValidExteriorRingPullQuantityInScanCode(Integer validExteriorRingPullQuantityInScanCode) {
		this.validExteriorRingPullQuantityInScanCode = validExteriorRingPullQuantityInScanCode;
	}

	public Integer getConventionalInvalidQuantityInScanCode() {
		return conventionalInvalidQuantityInScanCode;
	}

	public void setConventionalInvalidQuantityInScanCode(Integer conventionalInvalidQuantityInScanCode) {
		this.conventionalInvalidQuantityInScanCode = conventionalInvalidQuantityInScanCode;
	}

	public Integer getNotReportedQuantityInScanCode() {
		return notReportedQuantityInScanCode;
	}

	public void setNotReportedQuantityInScanCode(Integer notReportedQuantityInScanCode) {
		this.notReportedQuantityInScanCode = notReportedQuantityInScanCode;
	}

	public Integer getUnpaidQuantityInScanCode() {
		return unpaidQuantityInScanCode;
	}

	public void setUnpaidQuantityInScanCode(Integer unpaidQuantityInScanCode) {
		this.unpaidQuantityInScanCode = unpaidQuantityInScanCode;
	}

	public Integer getInvalidPaymentVersionQuantityInScanCode() {
		return invalidPaymentVersionQuantityInScanCode;
	}

	public void setInvalidPaymentVersionQuantityInScanCode(Integer invalidPaymentVersionQuantityInScanCode) {
		this.invalidPaymentVersionQuantityInScanCode = invalidPaymentVersionQuantityInScanCode;
	}

	public Integer getInvalidReportNumberQuantityInScanCode() {
		return invalidReportNumberQuantityInScanCode;
	}

	public void setInvalidReportNumberQuantityInScanCode(Integer invalidReportNumberQuantityInScanCode) {
		this.invalidReportNumberQuantityInScanCode = invalidReportNumberQuantityInScanCode;
	}

	public Integer getUnconventionalInvalidQuantityInScanCode() {
		return unconventionalInvalidQuantityInScanCode;
	}

	public void setUnconventionalInvalidQuantityInScanCode(Integer unconventionalInvalidQuantityInScanCode) {
		this.unconventionalInvalidQuantityInScanCode = unconventionalInvalidQuantityInScanCode;
	}

	public String getUnconventionalInvalidReasonInScanCode() {
		return unconventionalInvalidReasonInScanCode;
	}

	public void setUnconventionalInvalidReasonInScanCode(String unconventionalInvalidReasonInScanCode) {
		this.unconventionalInvalidReasonInScanCode = unconventionalInvalidReasonInScanCode;
	}

	public Calendar getStartTimeOfFirstVerification() {
		return startTimeOfFirstVerification;
	}

	public void setStartTimeOfFirstVerification(Calendar startTimeOfFirstVerification) {
		this.startTimeOfFirstVerification = startTimeOfFirstVerification;
	}

	public Calendar getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Calendar saveTime) {
		this.saveTime = saveTime;
	}

	public Calendar getEndTimeOfFirstVerification() {
		return endTimeOfFirstVerification;
	}

	public void setEndTimeOfFirstVerification(Calendar endTimeOfFirstVerification) {
		this.endTimeOfFirstVerification = endTimeOfFirstVerification;
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

	public Integer getValidExteriorRingQuantityInRecheck() {
		return validExteriorRingQuantityInRecheck;
	}

	public void setValidExteriorRingQuantityInRecheck(Integer validExteriorRingQuantityInRecheck) {
		this.validExteriorRingQuantityInRecheck = validExteriorRingQuantityInRecheck;
	}

	public Integer getInvalidExteriorRingQuantityInRecheck() {
		return invalidExteriorRingQuantityInRecheck;
	}

	public void setInvalidExteriorRingQuantityInRecheck(Integer invalidExteriorRingQuantityInRecheck) {
		this.invalidExteriorRingQuantityInRecheck = invalidExteriorRingQuantityInRecheck;
	}

	public Integer getValidCapQuantityInRecheck() {
		return validCapQuantityInRecheck;
	}

	public void setValidCapQuantityInRecheck(Integer validCapQuantityInRecheck) {
		this.validCapQuantityInRecheck = validCapQuantityInRecheck;
	}

	public Integer getInvalidCapQuantityInRecheck() {
		return invalidCapQuantityInRecheck;
	}

	public void setInvalidCapQuantityInRecheck(Integer invalidCapQuantityInRecheck) {
		this.invalidCapQuantityInRecheck = invalidCapQuantityInRecheck;
	}

	public Integer getValidQuantity() {
		return validQuantity;
	}

	public void setValidQuantity(Integer validQuantity) {
		this.validQuantity = validQuantity;
	}

	public Integer getInvalidQuantity() {
		return invalidQuantity;
	}

	public void setInvalidQuantity(Integer invalidQuantity) {
		this.invalidQuantity = invalidQuantity;
	}

	public String getUnconventionalInvalidReasonOfExteriorRing() {
		return unconventionalInvalidReasonOfExteriorRing;
	}

	public void setUnconventionalInvalidReasonOfExteriorRing(String unconventionalInvalidReasonOfExteriorRing) {
		this.unconventionalInvalidReasonOfExteriorRing = unconventionalInvalidReasonOfExteriorRing;
	}

	public String getUnconventionalInvalidReasonOfCap() {
		return unconventionalInvalidReasonOfCap;
	}

	public void setUnconventionalInvalidReasonOfCap(String unconventionalInvalidReasonOfCap) {
		this.unconventionalInvalidReasonOfCap = unconventionalInvalidReasonOfCap;
	}

	public Employee getRechecker() {
		return rechecker;
	}

	public void setRechecker(Employee rechecker) {
		this.rechecker = rechecker;
	}

	public Calendar getReckeckTime() {
		return reckeckTime;
	}

	public void setReckeckTime(Calendar reckeckTime) {
		this.reckeckTime = reckeckTime;
	}

	public Employee getToVerifyPerson() {
		return toVerifyPerson;
	}

	public void setToVerifyPerson(Employee toVerifyPerson) {
		this.toVerifyPerson = toVerifyPerson;
	}

	public Calendar getToVerifyTime() {
		return toVerifyTime;
	}

	public void setToVerifyTime(Calendar toVerifyTime) {
		this.toVerifyTime = toVerifyTime;
	}
}

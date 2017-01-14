package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.BonusActivityType;
import com.sap.jnc.marketing.persistence.enumeration.BonusDispatchStatus;
import com.sap.jnc.marketing.persistence.enumeration.BonusDispatchType;

@Entity
@Table(name = "T_INDIVIDUAL_PRODUCT_BONUS", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class IndividualProductBonus extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -2056773344431982074L;

	@Id
	@Column(name = "capInnerCode")
	private String capInnerCode;

	@Column(name = "year")
	private String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

	@Column(name = "month")
	private String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));

	@Column(name = "wechatBonusDispatchType")
	@Enumerated(EnumType.STRING)
	private BonusDispatchType wechatBonusDispatchType;

	@Column(name = "wechatBonusDispatchStatus")
	@Enumerated(EnumType.STRING)
	private BonusDispatchStatus wechatBonusDispatchStatus;

	@Column(name = "verificationCode")
	private String verificationCode;

	@Column(name = "activityName")
	private String activityName;

	@Column(name = "activityType")
	@Enumerated(EnumType.STRING)
	private BonusActivityType activityType;

	@Column(name = "wechatSubscriptionAccountAppId")
	private String wechatSubscriptionAccountAppId;

	@Column(name = "consumerOpenId")
	private String consumerOpenId;

	@Column(name = "wechatBillNumber")
	private String wechatBillNumber;

	@Column(name = "wechatPaymentAccountId")
	private String wechatPaymentAccountId;

	@Column(name = "wechatBonusNumber")
	private String wechatBonusNumber;

	@Column(name = "consumedTime")
	private Calendar consumedTime;

	@Column(name = "dispatchTime")
	private Calendar dispatchTime;

	@Column(name = "receiveTime")
	private Calendar receiveTime;

	@Column(name = "refundTime")
	private Calendar refundTime;

	@Column(name = "isConsumed")
	private Boolean isConsumed;

	@Column(name = "message")
	private String message;

	@Column(name = "errorCode")
	private String errorCode;

	@Column(name = "consumerAddressProvince")
	private String consumerAddressProvince;

	@Column(name = "consumerAddressCity")
	private String consumerAddressCity;

	@Column(name = "consumerAddressCounty")
	private String consumerAddressCounty;

	@Column(name = "consumerAddressLatitude")
	private String consumerAddressLatitude;

	@Column(name = "consumerAddressLongtitude")
	private String consumerAddressLongtitude;

	@Column(name = "consumerAddress")
	private String consumerAddress;

	private DispatchAmount wechatBonusAmount;

	private RefundAmount wechatRefundAmount;

	private ReceiveAmount receiveAmount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productErpCategoryId")
	private ProductErpCategory productErpCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "capInnerCode", referencedColumnName = "capInnerCode") })
	private IndividualProduct individualProduct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumerId")
	private Consumer consumer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dealerId")
	private Dealer dealer;

	public String getCapInnerCode() {
		return this.capInnerCode;
	}

	public void setCapInnerCode(String capInnerCode) {
		this.capInnerCode = capInnerCode;
	}

	public BonusDispatchType getWechatBonusDispatchType() {
		return this.wechatBonusDispatchType;
	}

	public void setWechatBonusDispatchType(BonusDispatchType wechatBonusDispatchType) {
		this.wechatBonusDispatchType = wechatBonusDispatchType;
	}

	public BonusDispatchStatus getWechatBonusDispatchStatus() {
		return this.wechatBonusDispatchStatus;
	}

	public void setWechatBonusDispatchStatus(BonusDispatchStatus wechatBonusDispatchStatus) {
		this.wechatBonusDispatchStatus = wechatBonusDispatchStatus;
	}

	public String getVerificationCode() {
		return this.verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getWechatSubscriptionAccountAppId() {
		return this.wechatSubscriptionAccountAppId;
	}

	public void setWechatSubscriptionAccountAppId(String wechatSubscriptionAccountAppId) {
		this.wechatSubscriptionAccountAppId = wechatSubscriptionAccountAppId;
	}

	public String getConsumerOpenId() {
		return this.consumerOpenId;
	}

	public void setConsumerOpenId(String consumerOpenId) {
		this.consumerOpenId = consumerOpenId;
	}

	public String getWechatBillNumber() {
		return this.wechatBillNumber;
	}

	public void setWechatBillNumber(String wechatBillNumber) {
		this.wechatBillNumber = wechatBillNumber;
	}

	public String getWechatPaymentAccountId() {
		return this.wechatPaymentAccountId;
	}

	public void setWechatPaymentAccountId(String wechatPaymentAccountId) {
		this.wechatPaymentAccountId = wechatPaymentAccountId;
	}

	public String getWechatBonusNumber() {
		return this.wechatBonusNumber;
	}

	public void setWechatBonusNumber(String wechatBonusNumber) {
		this.wechatBonusNumber = wechatBonusNumber;
	}

	public Calendar getConsumedTime() {
		return this.consumedTime;
	}

	public void setConsumedTime(Calendar consumedTime) {
		this.consumedTime = consumedTime;
	}

	public Calendar getDispatchTime() {
		return this.dispatchTime;
	}

	public void setDispatchTime(Calendar dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public Calendar getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Calendar receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Calendar getRefundTime() {
		return this.refundTime;
	}

	public void setRefundTime(Calendar refundTime) {
		this.refundTime = refundTime;
	}

	public Boolean getIsConsumed() {
		return this.isConsumed;
	}

	public void setIsConsumed(Boolean isConsumed) {
		this.isConsumed = isConsumed;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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

	public DispatchAmount getWechatBonusAmount() {
		return this.wechatBonusAmount;
	}

	public void setWechatBonusAmount(DispatchAmount wechatBonusAmount) {
		this.wechatBonusAmount = wechatBonusAmount;
	}

	public RefundAmount getWechatRefundAmount() {
		return this.wechatRefundAmount;
	}

	public void setWechatRefundAmount(RefundAmount wechatRefundAmount) {
		this.wechatRefundAmount = wechatRefundAmount;
	}

	public ReceiveAmount getReceiveAmount() {
		return this.receiveAmount;
	}

	public void setReceiveAmount(ReceiveAmount receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public IndividualProduct getIndividualProduct() {
		return this.individualProduct;
	}

	public void setIndividualProduct(IndividualProduct individualProduct) {
		this.individualProduct = individualProduct;
	}

	public Consumer getConsumer() {
		return this.consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public String getConsumerAddressProvince() {
		return this.consumerAddressProvince;
	}

	public void setConsumerAddressProvince(String consumerAddressProvince) {
		this.consumerAddressProvince = consumerAddressProvince;
	}

	public String getConsumerAddressCity() {
		return this.consumerAddressCity;
	}

	public void setConsumerAddressCity(String consumerAddressCity) {
		this.consumerAddressCity = consumerAddressCity;
	}

	public String getConsumerAddressCounty() {
		return this.consumerAddressCounty;
	}

	public void setConsumerAddressCounty(String consumerAddressCounty) {
		this.consumerAddressCounty = consumerAddressCounty;
	}

	public String getConsumerAddressLatitude() {
		return this.consumerAddressLatitude;
	}

	public void setConsumerAddressLatitude(String consumerAddressLatitude) {
		this.consumerAddressLatitude = consumerAddressLatitude;
	}

	public String getConsumerAddressLongtitude() {
		return this.consumerAddressLongtitude;
	}

	public void setConsumerAddressLongtitude(String consumerAddressLongtitude) {
		this.consumerAddressLongtitude = consumerAddressLongtitude;
	}

	public String getConsumerAddress() {
		return this.consumerAddress;
	}

	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}

	public ProductErpCategory getProductErpCategory() {
		return productErpCategory;
	}

	public void setProductErpCategory(ProductErpCategory productErpCategory) {
		this.productErpCategory = productErpCategory;
	}

	public BonusActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(BonusActivityType activityType) {
		this.activityType = activityType;
	}
}

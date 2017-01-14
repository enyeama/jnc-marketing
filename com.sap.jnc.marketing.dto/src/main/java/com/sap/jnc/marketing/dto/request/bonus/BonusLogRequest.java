package com.sap.jnc.marketing.dto.request.bonus;

import java.util.Date;

/**
 * Created by dy on 16/6/21.
 */
public class BonusLogRequest {

	private String capInnerCode;

	private String year;

	private String month;

	private String verificationCode;

	private Date consumedTime;

	private String dealerId;

	private String consumerId;

	private String wechatSubscriptionAccountAppId;

	private String wechatBillNumber;

	private String wechatPaymentAccountId;

	public String getCapInnerCode() {
		return capInnerCode;
	}

	public void setCapInnerCode(String capInnerCode) {
		this.capInnerCode = capInnerCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getConsumedTime() {
		return consumedTime;
	}

	public void setConsumedTime(Date consumedTime) {
		this.consumedTime = consumedTime;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getWechatSubscriptionAccountAppId() {
		return wechatSubscriptionAccountAppId;
	}

	public void setWechatSubscriptionAccountAppId(String wechatSubscriptionAccountAppId) {
		this.wechatSubscriptionAccountAppId = wechatSubscriptionAccountAppId;
	}

	public String getWechatBillNumber() {
		return wechatBillNumber;
	}

	public void setWechatBillNumber(String wechatBillNumber) {
		this.wechatBillNumber = wechatBillNumber;
	}

	public String getWechatPaymentAccountId() {
		return wechatPaymentAccountId;
	}

	public void setWechatPaymentAccountId(String wechatPaymentAccountId) {
		this.wechatPaymentAccountId = wechatPaymentAccountId;
	}
}

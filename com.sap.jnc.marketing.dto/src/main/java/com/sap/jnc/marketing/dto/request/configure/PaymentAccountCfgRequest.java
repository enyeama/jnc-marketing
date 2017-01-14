package com.sap.jnc.marketing.dto.request.configure;

import java.util.Calendar;

/**
 * Created by dy on 16/6/16.
 */
public class PaymentAccountCfgRequest {

	private Long defaultAccountID;

	private String defaultAccountOpenId;

	private Calendar validityPeriodValidFrom;

	private Calendar validityPeriodValidTo;

	public Long getDefaultAccountID() {
		return defaultAccountID;
	}

	public void setDefaultAccountID(Long defaultAccountID) {
		this.defaultAccountID = defaultAccountID;
	}

	public String getDefaultAccountOpenId() {
		return defaultAccountOpenId;
	}

	public void setDefaultAccountOpenId(String defaultAccountOpenId) {
		this.defaultAccountOpenId = defaultAccountOpenId;
	}

	public Calendar getValidityPeriodValidFrom() {
		return validityPeriodValidFrom;
	}

	public void setValidityPeriodValidFrom(Calendar validityPeriodValidFrom) {
		this.validityPeriodValidFrom = validityPeriodValidFrom;
	}

	public Calendar getValidityPeriodValidTo() {
		return validityPeriodValidTo;
	}

	public void setValidityPeriodValidTo(Calendar validityPeriodValidTo) {
		this.validityPeriodValidTo = validityPeriodValidTo;
	}
}

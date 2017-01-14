package com.sap.jnc.marketing.dto.request.configure;

import java.util.Calendar;

/**
 * Created by dy on 16/6/16.
 */
public class PaymentBonusRuleRequest {

	private String categoryId;

	private String averageCurrencyCode;

	private Double averageContent;

	private String varianceCurrencyCode;

	private Double varianceContent;

	private Calendar validityPeriodValidFrom;

	private Calendar validityPeriodValidTo;

	private PaymentBonusRuleItemRequest[] paymentBonusRuleItemRequests;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getAverageCurrencyCode() {
		return averageCurrencyCode;
	}

	public void setAverageCurrencyCode(String averageCurrencyCode) {
		this.averageCurrencyCode = averageCurrencyCode;
	}

	public Double getAverageContent() {
		return averageContent;
	}

	public void setAverageContent(Double averageContent) {
		this.averageContent = averageContent;
	}

	public String getVarianceCurrencyCode() {
		return varianceCurrencyCode;
	}

	public void setVarianceCurrencyCode(String varianceCurrencyCode) {
		this.varianceCurrencyCode = varianceCurrencyCode;
	}

	public Double getVarianceContent() {
		return varianceContent;
	}

	public void setVarianceContent(Double varianceContent) {
		this.varianceContent = varianceContent;
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

	public PaymentBonusRuleItemRequest[] getPaymentBonusRuleItemRequests() {
		return paymentBonusRuleItemRequests;
	}

	public void setPaymentBonusRuleItemRequests(PaymentBonusRuleItemRequest[] paymentBonusRuleItemRequests) {
		this.paymentBonusRuleItemRequests = paymentBonusRuleItemRequests;
	}
}

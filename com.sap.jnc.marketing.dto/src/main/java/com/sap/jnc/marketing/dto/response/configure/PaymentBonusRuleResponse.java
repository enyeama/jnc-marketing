package com.sap.jnc.marketing.dto.response.configure;

import java.util.Calendar;

/**
 * Created by dy on 16/6/16.
 */
public class PaymentBonusRuleResponse {

	private String categoryId;

	private String averageCurrencyCode;

	private Double averageContent;

	private String varianceCurrencyCode;

	private Double varianceContent;

	private Calendar validityPeriodValidFrom;

	private Calendar validityPeriodValidTo;

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
}

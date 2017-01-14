package com.sap.jnc.marketing.dto.request.configure;

/**
 * Created by dy on 16/6/16.
 */
public class PaymentBonusRuleItemRequest {

	private String amountCurrencyCode;

	private Double amountContent;

	private Double percent;

	public String getAmountCurrencyCode() {
		return amountCurrencyCode;
	}

	public void setAmountCurrencyCode(String amountCurrencyCode) {
		this.amountCurrencyCode = amountCurrencyCode;
	}

	public Double getAmountContent() {
		return amountContent;
	}

	public void setAmountContent(Double amountContent) {
		this.amountContent = amountContent;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}
}

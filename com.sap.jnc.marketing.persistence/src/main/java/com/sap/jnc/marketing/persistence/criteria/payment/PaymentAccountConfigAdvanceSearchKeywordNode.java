package com.sap.jnc.marketing.persistence.criteria.payment;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

public class PaymentAccountConfigAdvanceSearchKeywordNode implements SearchKeywordNode {

	private static final long serialVersionUID = -123974145093056644L;

	/** 接收查询字段 */
	private String validFrom;

	private String validTo;

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	@Override
	public String toString() {
		return "PaymentAccountConfigAdvanceSearchKeywordNode [validFrom=" + validFrom + ", validTo=" + validTo + "]";
	}

}

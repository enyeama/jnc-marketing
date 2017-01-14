package com.sap.jnc.marketing.dto.request.sparematerial;

import java.math.BigDecimal;

/*
 * @author Kay, Du I326950
 */

public class SpareMaterialPaymentWechatRequest {
	private String materialId;
	private BigDecimal paidQuantityValue;
	private String paymentComment;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public BigDecimal getPaidQuantityValue() {
		return paidQuantityValue;
	}

	public void setPaidQuantityValue(BigDecimal paidQuantityValue) {
		this.paidQuantityValue = paidQuantityValue;
	}

	public String getPaymentComment() {
		return paymentComment;
	}

	public void setPaymentComment(String paymentComment) {
		this.paymentComment = paymentComment;
	}
}

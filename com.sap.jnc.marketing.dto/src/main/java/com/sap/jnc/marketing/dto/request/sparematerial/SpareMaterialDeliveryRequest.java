package com.sap.jnc.marketing.dto.request.sparematerial;

import java.math.BigDecimal;

/*
 * @author Kay, Du I326950
 */

public class SpareMaterialDeliveryRequest {
	private BigDecimal deliveryQuantityValue;
	private String deliveryComment;

	public BigDecimal getDeliveryQuantityValue() {
		return deliveryQuantityValue;
	}

	public void setDeliveryQuantityValue(BigDecimal deliveryQuantityValue) {
		this.deliveryQuantityValue = deliveryQuantityValue;
	}

	public String getDeliveryComment() {
		return deliveryComment;
	}

	public void setDeliveryComment(String deliveryComment) {
		this.deliveryComment = deliveryComment;
	}
}

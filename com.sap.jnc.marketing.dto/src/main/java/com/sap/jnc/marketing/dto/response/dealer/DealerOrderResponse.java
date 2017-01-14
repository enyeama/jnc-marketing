package com.sap.jnc.marketing.dto.response.dealer;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.TerminalOrder;

public class DealerOrderResponse implements Serializable {

	private static final long serialVersionUID = -7575541571666840620L;

	private String orderId;
	
	public DealerOrderResponse(TerminalOrder entity) {
		this.orderId = entity.getId().toString();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}

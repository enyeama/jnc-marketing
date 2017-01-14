package com.sap.jnc.marketing.dto.request.dealer;

import java.io.Serializable;

public class DealerOrderRequest implements Serializable {

	private static final long serialVersionUID = 2934545387643585317L;

	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}

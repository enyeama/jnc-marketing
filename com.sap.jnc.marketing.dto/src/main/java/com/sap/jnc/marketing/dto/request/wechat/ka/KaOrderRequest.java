package com.sap.jnc.marketing.dto.request.wechat.ka;

import java.io.Serializable;

public class KaOrderRequest implements Serializable {

	private static final long serialVersionUID = 776520832465139614L;

	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}

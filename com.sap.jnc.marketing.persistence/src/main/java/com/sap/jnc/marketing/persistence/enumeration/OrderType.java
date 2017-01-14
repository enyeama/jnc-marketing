package com.sap.jnc.marketing.persistence.enumeration;

public enum OrderType {

	DEALER_ORDER("经销商订单"), TERMINAL_ORDER("终端订单");

	private String description;

	private OrderType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}

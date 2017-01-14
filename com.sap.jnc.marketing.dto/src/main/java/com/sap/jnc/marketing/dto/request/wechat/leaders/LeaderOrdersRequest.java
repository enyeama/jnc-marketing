package com.sap.jnc.marketing.dto.request.wechat.leaders;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;

public class LeaderOrdersRequest implements Serializable {

	private static final long serialVersionUID = 1919461616459632260L;

	private String leaderId;
	
	private TerminalOrderStatus status;
	
	private String orderId; 

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public TerminalOrderStatus getStatus() {
		return status;
	}

	public void setStatus(TerminalOrderStatus status) {
		this.status = status;
	}
}

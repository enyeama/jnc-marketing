package com.sap.jnc.marketing.dto.request.wechat.terminal;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;

public class TerminalOrdersRequest implements Serializable {

	private static final long serialVersionUID = -1382055825041616779L;

	private String terminalId;
	
	private TerminalOrderStatus status;
	
	private String orderId;

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public TerminalOrderStatus getStatus() {
		return status;
	}

	public void setStatus(TerminalOrderStatus status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}

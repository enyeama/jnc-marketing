package com.sap.jnc.marketing.dto.request.wechat.salesman;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;

public class SalesMyOrdersRequest implements Serializable {

	private static final long serialVersionUID = 856518706577858033L;

	private String salesmanId;

	private String terminalId;

	private TerminalOrderStatus status;

	public String getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

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
}

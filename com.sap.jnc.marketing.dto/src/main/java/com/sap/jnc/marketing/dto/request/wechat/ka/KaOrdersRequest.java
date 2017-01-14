package com.sap.jnc.marketing.dto.request.wechat.ka;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;

public class KaOrdersRequest implements Serializable {

	private static final long serialVersionUID = -5520285404156222940L;

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

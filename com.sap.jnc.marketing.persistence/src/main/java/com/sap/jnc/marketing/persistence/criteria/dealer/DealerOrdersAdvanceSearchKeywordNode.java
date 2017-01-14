package com.sap.jnc.marketing.persistence.criteria.dealer;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;

public class DealerOrdersAdvanceSearchKeywordNode implements SearchKeywordNode {

	private static final long serialVersionUID = -6728264961031996770L;

	private String orderId;
	
	private TerminalOrderStatus status;
	
	private String terminal;

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

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
}

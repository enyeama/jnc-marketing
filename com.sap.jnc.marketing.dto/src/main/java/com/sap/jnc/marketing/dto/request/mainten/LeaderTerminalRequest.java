package com.sap.jnc.marketing.dto.request.mainten;

import java.io.Serializable;

public class LeaderTerminalRequest implements Serializable {

	/**
	 * @author I326998
	 */
	private static final long serialVersionUID = -615093042535975851L;
	
	private String salesmanExternalId;
	private Long terminalId;
	public String getSalesmanExternalId() {
		return salesmanExternalId;
	}
	public void setSalesmanExternalId(String salesmanExternalId) {
		this.salesmanExternalId = salesmanExternalId;
	}
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	

}

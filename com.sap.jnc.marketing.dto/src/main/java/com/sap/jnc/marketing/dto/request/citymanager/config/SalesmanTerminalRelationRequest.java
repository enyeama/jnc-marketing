/**
 * 
 */
package com.sap.jnc.marketing.dto.request.citymanager.config;

/**
 * @author Quansheng Liu I075496
 */
public class SalesmanTerminalRelationRequest {
	private long positionId;
	private long terminalId;

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	public long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(long terminalId) {
		this.terminalId = terminalId;
	}

}

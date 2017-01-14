/**
 * 
 */
package com.sap.jnc.marketing.dto.response;

/**
 * @author Quansheng Liu I075496
 */
public class SalesmanTerminalRelationResponse {
	private long positionId;
	private String positionName;
	private long terminalId;
	private String terminalTitle;
	private String terminalDescription;

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(long terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalTitle() {
		return terminalTitle;
	}

	public void setTerminalTitle(String terminalTitle) {
		this.terminalTitle = terminalTitle;
	}

	public String getTerminalDescription() {
		return terminalDescription;
	}

	public void setTerminalDescription(String terminalDescription) {
		this.terminalDescription = terminalDescription;
	}

}

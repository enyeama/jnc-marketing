package com.sap.jnc.marketing.dto.response.kadd;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Maggie Liu
 */

public class KATerminalDetailResponse implements Serializable {
	private static final long serialVersionUID = 18336610566517314L;

	private String kaName;
	private Long kaId;
	private String kaSystemName;
	private String kaAddress;
	private String maintainerName;
	private String maintainerPosition;
	private String maintainerPositionId;
	private String maintainerEmployeeId;
	private String officeName;
	private String officeId;

	public KATerminalDetailResponse(Terminal terminal) {
		if (terminal == null) {
			return;
		}
		this.kaName = terminal.getBranchName() != null ? terminal.getBranchName() : "";
		this.kaId = terminal.getId() != null ? terminal.getId() : null;
		this.kaSystemName = terminal.getKASystemName() != null ? terminal.getKASystemName() : "";
		this.kaAddress = terminal.getAddress() != null ? terminal.getAddress() : "";
		if (terminal.getMaintainer() != null) {
			this.maintainerPosition=terminal.getMaintainer().getName()!=null?terminal.getMaintainer().getName():"";
			this.maintainerPositionId=terminal.getMaintainer().getExternalId()!=null?terminal.getMaintainer().getExternalId():"";
			if(terminal.getMaintainer().getEmployee()!=null){
				this.maintainerName = terminal.getMaintainer().getEmployee().getName() != null ? terminal.getMaintainer().getEmployee().getName() : "";
				this.maintainerEmployeeId=terminal.getMaintainer().getEmployee().getExternalId()!=null?terminal.getMaintainer().getEmployee().getExternalId():"";
			}
			
		}
		if (terminal.getKAOffice() != null) {
			this.officeName = terminal.getKAOffice().getName() != null ? terminal.getKAOffice().getName() : "";
			this.officeId=terminal.getKAOffice().getExternalId()!=null? terminal.getKAOffice().getExternalId():"";
		}
	}

	public String getKaName() {
		return kaName;
	}

	public void setKaName(String kaName) {
		this.kaName = kaName;
	}

	public Long getKaId() {
		return kaId;
	}

	public void setKaId(Long kaId) {
		this.kaId = kaId;
	}

	public String getKaSystemName() {
		return kaSystemName;
	}

	public void setKaSystemName(String kaSystemName) {
		this.kaSystemName = kaSystemName;
	}

	public String getKaAddress() {
		return kaAddress;
	}

	public void setKaAddress(String kaAddress) {
		this.kaAddress = kaAddress;
	}

	public String getMaintainerName() {
		return maintainerName;
	}

	public void setMaintainerName(String maintainerName) {
		this.maintainerName = maintainerName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getMaintainerPosition() {
		return maintainerPosition;
	}

	public void setMaintainerPosition(String maintainerPosition) {
		this.maintainerPosition = maintainerPosition;
	}

	public String getMaintainerPositionId() {
		return maintainerPositionId;
	}

	public void setMaintainerPositionId(String maintainerPositionId) {
		this.maintainerPositionId = maintainerPositionId;
	}

	public String getMaintainerEmployeeId() {
		return maintainerEmployeeId;
	}

	public void setMaintainerEmployeeId(String maintainerEmployeeId) {
		this.maintainerEmployeeId = maintainerEmployeeId;
	}
	
}

package com.sap.jnc.marketing.dto.shared.banquet;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
public class BoxCasePair {
	private String boxId;
	private String caseId;
	
	public BoxCasePair(){};
	
	public BoxCasePair(String boxId, String caseId){
		this.setBoxId(boxId);
		this.setCaseId(caseId);
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

}

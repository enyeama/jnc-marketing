package com.sap.jnc.marketing.dto.request.admin;

import java.io.Serializable;

public class DealerSMInRequest implements Serializable {

	private static final long serialVersionUID = -7532615545667386231L;
	
	private String[] caseIDList;

	public String[] getCaseIDList() {
		return caseIDList;
	}

	public void setCaseIDList(String[] caseIDList) {
		this.caseIDList = caseIDList;
	}
}

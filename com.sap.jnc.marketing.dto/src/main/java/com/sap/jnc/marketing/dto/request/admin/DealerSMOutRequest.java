package com.sap.jnc.marketing.dto.request.admin;

import java.io.Serializable;

public class DealerSMOutRequest implements Serializable {

	private static final long serialVersionUID = 7795742735634637196L;

	private String[] caseIDList;
	
	private String leaderId;

	public String[] getCaseIDList() {
		return caseIDList;
	}

	public void setCaseIDList(String[] caseIDList) {
		this.caseIDList = caseIDList;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
}

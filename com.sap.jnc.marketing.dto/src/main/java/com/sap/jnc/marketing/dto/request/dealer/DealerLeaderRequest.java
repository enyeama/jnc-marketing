package com.sap.jnc.marketing.dto.request.dealer;

import java.io.Serializable;

public class DealerLeaderRequest implements Serializable {

	private static final long serialVersionUID = -6758774021860191901L;
	
	private String dealerId;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
}

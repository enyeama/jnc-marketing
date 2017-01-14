package com.sap.jnc.marketing.dto.request.mainten;

import java.io.Serializable;

/**
 * @author Maggie Liu
 */

public class DLPRelationRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long dealerId;
	private String leaderId;
	private String productId;
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
	
}

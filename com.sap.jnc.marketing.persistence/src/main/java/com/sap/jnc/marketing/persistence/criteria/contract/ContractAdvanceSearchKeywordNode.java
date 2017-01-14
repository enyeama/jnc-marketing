package com.sap.jnc.marketing.persistence.criteria.contract;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.ContractStatus;

public class ContractAdvanceSearchKeywordNode implements SearchKeywordNode {

	private static final long serialVersionUID = -8449073283421199858L;

	private String contractId;

	private String dealerId;

	private String positionId;

	private String productDmsCategoryId;

	private ContractStatus status;

	public String getProductDmsCategoryId() {
		return productDmsCategoryId;
	}

	public void setProductDmsCategoryId(String productDmsCategoryId) {
		this.productDmsCategoryId = productDmsCategoryId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public ContractStatus getStatus() {
		return status;
	}

	public void setStatus(ContractStatus status) {
		this.status = status;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

}

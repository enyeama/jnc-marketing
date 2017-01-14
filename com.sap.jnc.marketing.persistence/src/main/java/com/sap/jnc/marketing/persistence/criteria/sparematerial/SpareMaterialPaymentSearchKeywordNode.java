package com.sap.jnc.marketing.persistence.criteria.sparematerial;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

/**
 * @author Kay, Du I326950
 */

public class SpareMaterialPaymentSearchKeywordNode implements SearchKeywordNode {
	private static final long serialVersionUID = 2933599279984310699L;

	private String positionId;

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
}

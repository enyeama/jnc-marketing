package com.sap.jnc.marketing.persistence.criteria.dealer;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;

public class DealerAdvanceSearchKeywordNode implements SearchKeywordNode {

	private static final long serialVersionUID = -6521056151258865223L;

	private String externalId;

	private String legalContactName;

	private DealerStatus status;

	private Boolean isPlatformDealer;

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getLegalContactName() {
		return this.legalContactName;
	}

	public void setLegalContactName(String legalContactName) {
		this.legalContactName = legalContactName;
	}

	public DealerStatus getStatus() {
		return this.status;
	}

	public void setStatus(DealerStatus status) {
		this.status = status;
	}

	public Boolean getIsPlatformDealer() {
		return this.isPlatformDealer;
	}

	public void setIsPlatformDealer(Boolean isPlatformDealer) {
		this.isPlatformDealer = isPlatformDealer;
	}
}

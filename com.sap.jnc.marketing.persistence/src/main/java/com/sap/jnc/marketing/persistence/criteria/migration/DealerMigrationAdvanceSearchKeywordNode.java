package com.sap.jnc.marketing.persistence.criteria.migration;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;

/**
 * @author I323691 Marco Huang
 */
public class DealerMigrationAdvanceSearchKeywordNode implements SearchKeywordNode {

	/**
	 * Generate serial id
	 */
	private static final long serialVersionUID = 226300793959256450L;

	private String dealerId;
	private String dealerName;
	private DealerStatus dealerStatus;
	private String isPlatform;
	private String isPrimaryDealer;
	private String cityManagerPositionId;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public void setDealerStatus(DealerStatus dealerStatus) {
		this.dealerStatus = dealerStatus;
	}

	public DealerStatus getDealerStatus() {
		return this.dealerStatus;
	}

	public String getIsPlatform() {
		return isPlatform;
	}

	public void setIsPlatform(String isPlatform) {
		this.isPlatform = isPlatform;
	}

	public String getIsPrimaryDealer() {
		return isPrimaryDealer;
	}

	public void setIsPrimaryDealer(String isPrimaryDealer) {
		this.isPrimaryDealer = isPrimaryDealer;
	}

	public String getCityManagerPositionId() {
		return cityManagerPositionId;
	}

	public void setCityManagerPositionId(String cityManagerPositionId) {
		this.cityManagerPositionId = cityManagerPositionId;
	}
}

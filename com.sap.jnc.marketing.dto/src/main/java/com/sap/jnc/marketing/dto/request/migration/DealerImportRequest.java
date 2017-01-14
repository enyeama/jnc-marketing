package com.sap.jnc.marketing.dto.request.migration;

import java.io.Serializable;

/**
 * @author Kay, Du I326950
 */
public class DealerImportRequest implements Serializable {
	private static final long serialVersionUID = -3243020647799090804L;

	private String operationType;
	private String dealerExternalId;
	private String dealerName;
	private String dealerStatus;
	private String isPlatform;
	private String isPrimaryDealer;
	private String primaryDealerExternalId;
	private String cityManagerPositionId;

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getDealerExternalId() {
		return dealerExternalId;
	}

	public void setDealerExternalId(String dealerExternalId) {
		this.dealerExternalId = dealerExternalId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerStatus() {
		return dealerStatus;
	}

	public void setDealerStatus(String dealerStatus) {
		this.dealerStatus = dealerStatus;
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

	public String getPrimaryDealerExternalId() {
		return primaryDealerExternalId;
	}

	public void setPrimaryDealerExternalId(String primaryDealerExternalId) {
		this.primaryDealerExternalId = primaryDealerExternalId;
	}

	public String getCityManagerPositionId() {
		return cityManagerPositionId;
	}

	public void setCityManagerPositionId(String cityManagerPositionId) {
		this.cityManagerPositionId = cityManagerPositionId;
	}

}

/**
 * 
 */
package com.sap.jnc.marketing.dto.request.citymanager.config;

/**
 * @author Quansheng Liu I075496
 */
public class RelationSettingRequest {
	private long dealerId;
	private long positionId;
	private long salesCategoryId;

	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	public long getSalesCategoryId() {
		return salesCategoryId;
	}

	public void setSalesCategoryId(long salesCategoryId) {
		this.salesCategoryId = salesCategoryId;
	}

}

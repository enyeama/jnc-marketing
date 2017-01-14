/**
 * 
 */
package com.sap.jnc.marketing.dto.response;

/**
 * @author Quansheng Liu I075496
 */
public class RelationSettingResponse {
	private long dealerId;
	private String dealerName;
	private long positionId;
	private String positionName;
	private long salesCategoryId;
	private String salesCategoryName;

	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public long getSalesCategoryId() {
		return salesCategoryId;
	}

	public void setSalesCategoryId(long salesCategoryId) {
		this.salesCategoryId = salesCategoryId;
	}

	public String getSalesCategoryName() {
		return salesCategoryName;
	}

	public void setSalesCategoryName(String salesCategoryName) {
		this.salesCategoryName = salesCategoryName;
	}

}

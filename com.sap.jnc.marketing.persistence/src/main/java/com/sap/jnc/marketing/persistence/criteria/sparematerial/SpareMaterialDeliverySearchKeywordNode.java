package com.sap.jnc.marketing.persistence.criteria.sparematerial;

import java.util.Calendar;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.GiftListStatus;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialDeliveryStatus;

/**
 * @author Kay, Du I326950
 */

public class SpareMaterialDeliverySearchKeywordNode implements SearchKeywordNode {
	private static final long serialVersionUID = -3497482505872474701L;

	private String positionId;
	private String materialId;
	private Calendar startDate;
	private Calendar endDate;
	private SpareMaterialDeliveryStatus deliveryStatus;
	private GiftListStatus giftListStatus;
	private String materialType;

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public SpareMaterialDeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(SpareMaterialDeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public GiftListStatus getGiftListStatus() {
		return giftListStatus;
	}

	public void setGiftListStatus(GiftListStatus giftListStatus) {
		this.giftListStatus = giftListStatus;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

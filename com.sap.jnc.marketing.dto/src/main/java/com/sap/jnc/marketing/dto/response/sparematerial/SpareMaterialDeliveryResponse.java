package com.sap.jnc.marketing.dto.response.sparematerial;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.sap.jnc.marketing.persistence.enumeration.GiftListStatus;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialDeliveryStatus;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;

/*
 * @author Kay, Du I326950
 */

public class SpareMaterialDeliveryResponse {
	private Long id;
	private String deliveryId;
	private SpareMaterialDeliveryStatus deliveryStatus;
	private GiftListStatus giftListStatus;
	private BigDecimal deliveryQuantityValue;
	private String deliveryDate;
	private String giftListExportDate;
	private String acknowledgementDate;
	private String comment;
	private String materialId;
	private String materialName;
	private String employeeName;
	private String positionId;
	private String positionName;

	public SpareMaterialDeliveryResponse(SpareMaterialDelivery spareMaterialDelivery) {
		this.setId(spareMaterialDelivery.getId());
		this.setDeliveryId(spareMaterialDelivery.getDeliveryId());
		this.setDeliveryStatus(spareMaterialDelivery.getDeliveryStatus());
		this.setGiftListStatus(spareMaterialDelivery.getGiftListStatus());
		this.setDeliveryQuantityValue(spareMaterialDelivery.getDeliveryQuantity().getValue());
		this.setDeliveryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spareMaterialDelivery.getDeliveryDate().getTime()));
		if(spareMaterialDelivery.getGiftListExportDate() == null) {
			this.setGiftListExportDate("");
		} else {
			this.setGiftListExportDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spareMaterialDelivery.getGiftListExportDate().getTime()));
		}
		
		if(spareMaterialDelivery.getAcknowledgementDate() == null) {
			this.setAcknowledgementDate("");
		} else {
			this.setAcknowledgementDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spareMaterialDelivery.getAcknowledgementDate().getTime()));
		}
		
		this.setComment(spareMaterialDelivery.getComment());
		this.setMaterialId(spareMaterialDelivery.getProduct().getId());
		this.setMaterialName(spareMaterialDelivery.getProduct().getName());
		this.setEmployeeName(spareMaterialDelivery.getEmployee().getName());
		this.setPositionId(spareMaterialDelivery.getPosition().getExternalId());
		this.setPositionName(spareMaterialDelivery.getPosition().getName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
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

	public BigDecimal getDeliveryQuantityValue() {
		return deliveryQuantityValue;
	}

	public void setDeliveryQuantityValue(BigDecimal deliveryQuantityValue) {
		this.deliveryQuantityValue = deliveryQuantityValue;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getGiftListExportDate() {
		return giftListExportDate;
	}

	public void setGiftListExportDate(String giftListExportDate) {
		this.giftListExportDate = giftListExportDate;
	}

	public String getAcknowledgementDate() {
		return acknowledgementDate;
	}

	public void setAcknowledgementDate(String acknowledgementDate) {
		this.acknowledgementDate = acknowledgementDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}

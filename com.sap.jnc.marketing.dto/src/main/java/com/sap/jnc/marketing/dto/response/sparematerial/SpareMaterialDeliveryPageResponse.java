package com.sap.jnc.marketing.dto.response.sparematerial;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.enumeration.GiftListStatus;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialDeliveryStatus;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;

/**
 * @author Kay, Du I326950
 */

public class SpareMaterialDeliveryPageResponse extends PageImpl<SpareMaterialDeliveryPageResponse.Item> implements Serializable {
	private static final long serialVersionUID = -3715725539496745853L;
	
	public SpareMaterialDeliveryPageResponse(Page<SpareMaterialDelivery> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(spareMaterialDelivery -> new Item(spareMaterialDelivery)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}
	
	public static class Item implements Serializable {
		private static final long serialVersionUID = 2698616481793177546L;
		
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
		
		public Item() {
		}

		public Item(SpareMaterialDelivery spareMaterialDelivery) {
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
			if(spareMaterialDelivery.getProduct() == null) {
				this.setMaterialId("");
				this.setMaterialName("");
			} else {
				this.setMaterialId(spareMaterialDelivery.getProduct().getId());
				this.setMaterialName(spareMaterialDelivery.getProduct().getName());
			}
			
			this.setEmployeeName(spareMaterialDelivery.getEmployee().getName());
			if(spareMaterialDelivery.getPosition() == null) {
				this.setPositionId("");
				this.setPositionName("");
			} else {
				this.setPositionId(spareMaterialDelivery.getPosition().getExternalId());
				this.setPositionName(spareMaterialDelivery.getPosition().getName());
			}
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
}

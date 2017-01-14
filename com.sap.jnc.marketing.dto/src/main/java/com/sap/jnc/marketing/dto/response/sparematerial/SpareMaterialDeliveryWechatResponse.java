package com.sap.jnc.marketing.dto.response.sparematerial;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialDeliveryStatus;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;

/*
 * @author I326950 Kay Du
 */

public class SpareMaterialDeliveryWechatResponse {
	private Long id;
	private SpareMaterialDeliveryStatus deliveryStatus;
	private BigDecimal deliveryQuantity;
	private String deliveryDate;
	private String acknowledgementDate;
	private String comment;
	private String product;

	public SpareMaterialDeliveryWechatResponse(SpareMaterialDelivery spareMaterialDelivery) {
		this.setId(spareMaterialDelivery.getId());
		this.setDeliveryStatus(spareMaterialDelivery.getDeliveryStatus());
		this.setDeliveryQuantity(spareMaterialDelivery.getDeliveryQuantity().getValue());
		this.setDeliveryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spareMaterialDelivery.getDeliveryDate().getTime()));
		
		if (spareMaterialDelivery.getAcknowledgementDate() != null) {
			this.setAcknowledgementDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spareMaterialDelivery.getAcknowledgementDate().getTime()));
		} else {
			this.setAcknowledgementDate(null);
		}
		
		this.setComment(spareMaterialDelivery.getComment());
		this.setProduct(spareMaterialDelivery.getProduct().getName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpareMaterialDeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(SpareMaterialDeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public BigDecimal getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(BigDecimal bigDecimal) {
		this.deliveryQuantity = bigDecimal;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}

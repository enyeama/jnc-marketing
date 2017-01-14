package com.sap.jnc.marketing.dto.response.sparematerial;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialDeliveryStatus;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;

/**
 * @author Kay, Du I326950
 */

public class SpareMaterialDeliveryPageWechatResponse extends PageImpl<SpareMaterialDeliveryPageWechatResponse.Item> implements Serializable {
	private static final long serialVersionUID = -7765019752428320626L;

	public SpareMaterialDeliveryPageWechatResponse(Page<SpareMaterialDelivery> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(spareMaterialDelivery -> new Item(spareMaterialDelivery)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {
		private static final long serialVersionUID = -8913091492822868829L;
		private Long id;
		private SpareMaterialDeliveryStatus deliveryStatus;
		private BigDecimal deliveryQuantity;
		private String deliveryDate;
		private String acknowledgementDate;
		private String comment;
		private String product;
		
		public Item() {
			
		}

		public Item(SpareMaterialDelivery spareMaterialDelivery) {
			this.setId(spareMaterialDelivery.getId());
			this.setDeliveryStatus(spareMaterialDelivery.getDeliveryStatus());
			this.setDeliveryQuantity(spareMaterialDelivery.getDeliveryQuantity().getValue());
			this.setDeliveryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spareMaterialDelivery.getDeliveryDate().getTime()));
			
			if (spareMaterialDelivery.getAcknowledgementDate() != null) {
				this.setAcknowledgementDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spareMaterialDelivery.getAcknowledgementDate().getTime()));
			} else {
				this.setAcknowledgementDate("");
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
}

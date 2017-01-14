package com.sap.jnc.marketing.dto.response.sparematerial;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment;

/**
 * @author Kay, Du I326950
 */

public class SpareMaterialPaymentPageWechatResponse extends PageImpl<SpareMaterialPaymentPageWechatResponse.Item> implements Serializable {
	private static final long serialVersionUID = -8288984318296226059L;

	public SpareMaterialPaymentPageWechatResponse(Page<SpareMaterialPayment> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(spareMaterialPayment -> new Item(spareMaterialPayment)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {
		private static final long serialVersionUID = -166313002270452906L;
		private Long id;
		private String paymentDate;
		private BigDecimal paidQuantity;
		private String paymentComment;
		private String product;

		public Item() {

		}

		public Item(SpareMaterialPayment spareMaterialPayment) {
			this.setId(spareMaterialPayment.getId());
			this.setPaymentDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(spareMaterialPayment.getPaymentDate().getTime()));
			this.setPaidQuantity(spareMaterialPayment.getPaidQuantity().getValue());
			this.setPaymentComment(spareMaterialPayment.getPaymentComment());
			this.setProduct(spareMaterialPayment.getProduct().getName());
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPaymentDate() {
			return paymentDate;
		}

		public void setPaymentDate(String paymentDate) {
			this.paymentDate = paymentDate;
		}

		public BigDecimal getPaidQuantity() {
			return paidQuantity;
		}

		public void setPaidQuantity(BigDecimal paidQuantity) {
			this.paidQuantity = paidQuantity;
		}

		public String getPaymentComment() {
			return paymentComment;
		}

		public void setPaymentComment(String paymentComment) {
			this.paymentComment = paymentComment;
		}

		public String getProduct() {
			return product;
		}

		public void setProduct(String product) {
			this.product = product;
		}
	}
}

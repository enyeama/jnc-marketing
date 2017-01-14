package com.sap.jnc.marketing.dto.response.sparematerial;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment;

/**
 * @author Kay, Du I326950
 */

public class SpareMaterialPaymentWechatResponse implements Serializable {
	private static final long serialVersionUID = -4686730044961566970L;
	private Long id;
	private String paymentDate;
	private BigDecimal paidQuantity;
	private String paymentComment;
	private String product;
	
	public SpareMaterialPaymentWechatResponse(SpareMaterialPayment spareMaterialPayment) {
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

	public void setPaidQuantity(BigDecimal bigDecimal) {
		this.paidQuantity = bigDecimal;
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

package com.sap.jnc.marketing.dto.response.wechat.salesman;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.TerminalOrder;

public class SalesOrderResponse implements Serializable {

	private static final long serialVersionUID = 856518706577858033L;

	private String orderNO;
	
	private String category;

	private String prodName;

	private int quantity;

	private boolean sms;

	private String comment;
	
	private String status;
	
	private int statusValue;

	public SalesOrderResponse(String id) {
		this.orderNO = id;
	}

	public SalesOrderResponse(TerminalOrder entity) {
		this.orderNO = entity.getId().toString();
		this.quantity = entity.getQuantity();
		this.status = entity.getStatus().getLabel();
		this.statusValue = entity.getStatus().getIntValue();
		this.comment = entity.getComment();
		if (entity.getProduct() != null) {
			this.prodName = entity.getProduct().getName();
			if (entity.getProduct().getProductDmsCategories() != null
				&& entity.getProduct().getProductDmsCategories().size() > 0) {
				this.category = entity.getProduct().getProductDmsCategories().get(0).getName();
			}
		}
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isSms() {
		return sms;
	}

	public void setSms(boolean sms) {
		this.sms = sms;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}
}

package com.sap.jnc.marketing.dto.request.wechat.salesman;

import java.io.Serializable;

public class SalesOrderRequest implements Serializable {

	private static final long serialVersionUID = 856518706577858033L;

	private String terminalID;

	private String materialID;

	private String quantity;

	private String comment;
	
	private String salesId;

	public String getTerminalID() {
		return terminalID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	public String getMaterialID() {
		return materialID;
	}

	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
}

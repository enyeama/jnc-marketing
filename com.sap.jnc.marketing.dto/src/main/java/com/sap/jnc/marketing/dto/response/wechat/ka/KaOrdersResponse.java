package com.sap.jnc.marketing.dto.response.wechat.ka;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.TerminalOrder;

public class KaOrdersResponse implements Serializable {

	private static final long serialVersionUID = 5720031806665683845L;

	private String orderNO;

	private String materialID;

	private String materialName;

	private int quantity;

	private String status;
	
	private int statusValue;

	private String address;

	private String tel;
	
	public KaOrdersResponse(TerminalOrder entity) {
		this.orderNO = entity.getId().toString();
		this.quantity = entity.getQuantity();
		this.status = entity.getStatus().getLabel();
		this.statusValue = entity.getStatus().getIntValue();
		if (entity.getProduct() != null) {
			this.materialID = String.valueOf(entity.getProduct().getId());
			this.materialName = entity.getProduct().getName();
		}
		if (entity.getTerminal() != null) {
			this.address = entity.getTerminal().getAddress();
			this.tel = entity.getTerminal().getPhone();
		}
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getMaterialID() {
		return materialID;
	}

	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}

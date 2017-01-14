package com.sap.jnc.marketing.dto.response.wechat.salesman;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author C5245167 Xiao Qi
 */
public class SalesMyOrdersResponse implements Serializable {

	private static final long serialVersionUID = 856518706577858033L;

	private String orderNO;

	private String materialID;

	private String materialName;

	private int quantity;

	private String status;
	
	private int statusValue;

	private String address;

	private String tel;

	public SalesMyOrdersResponse(TerminalOrder entity) {
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
			if (entity.getTerminal().getType() == TerminalType.HOTEL) {
				this.tel = entity.getTerminal().getKeyUserContact().getPhone();
			} else if (entity.getTerminal().getType() == TerminalType.SHOP) {
				this.tel = entity.getTerminal().getCashPersonMobile();
			}
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

package com.sap.jnc.marketing.dto.response.wechat.terminal;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

public class TerminalOrdersResponse implements Serializable {

	private static final long serialVersionUID = -5408832038861156230L;

	private String orderNO;
	
	private String materialID;
	
	private String materialName;
	
	private int quantity;
	
	private String status;
	
	private String terminalTitle;
	
	private String address;
	
	private String tel;
	
	private boolean isPTOrder;
	
	public TerminalOrdersResponse(TerminalOrder entity) {
		this.orderNO = entity.getId().toString();
		this.quantity = entity.getQuantity();
		this.status = entity.getStatus().getLabel();
		if (entity.getProduct() != null) {
			this.materialID = String.valueOf(entity.getProduct().getId());
			this.materialName = entity.getProduct().getName();
		}
		if (entity.getTerminal() != null) {
			this.terminalTitle = entity.getTerminal().getTitle();
			this.address = entity.getTerminal().getAddress();
			if (entity.getTerminal().getType() == TerminalType.HOTEL) {
				this.tel = entity.getTerminal().getKeyUserContact().getPhone();
			} else if (entity.getTerminal().getType() == TerminalType.SHOP) {
				this.tel = entity.getTerminal().getCashPersonMobile();
			}
		}
		if (entity.getResponsibleLeader() != null) {
			this.isPTOrder = true;
		} else if (entity.getDealer() != null) {
			this.isPTOrder = false;
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

	public String getTerminalTitle() {
		return terminalTitle;
	}

	public void setTerminalTitle(String terminalTitle) {
		this.terminalTitle = terminalTitle;
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

	public boolean isPTOrder() {
		return isPTOrder;
	}

	public void setPTOrder(boolean isPTOrder) {
		this.isPTOrder = isPTOrder;
	}
}

/**
 * 
 */
package com.sap.jnc.marketing.dto.request.logistic;

import com.sap.jnc.marketing.persistence.enumeration.MovementType;

/**
 * @author Quansheng Liu I075496
 */
public class CodeScanRequest {
	String qrCode;
	Long orderId;
	Long logisticFromId;
	Long logisticToId;
	MovementType movementType;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getLogisticFromId() {
		return logisticFromId;
	}

	public void setLogisticFromId(Long logisticFromId) {
		this.logisticFromId = logisticFromId;
	}

	public Long getLogisticToId() {
		return logisticToId;
	}

	public void setLogisticToId(Long logisticToId) {
		this.logisticToId = logisticToId;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

}

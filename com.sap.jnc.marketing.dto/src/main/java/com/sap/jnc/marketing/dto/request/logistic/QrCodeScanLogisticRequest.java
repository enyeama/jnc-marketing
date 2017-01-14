/**
 * 
 */
package com.sap.jnc.marketing.dto.request.logistic;

import java.util.List;

import com.sap.jnc.marketing.persistence.enumeration.LogisticOperatorType;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.enumeration.OrderType;

/**
 * @author I075496 Quansheng Liu
 */
public class QrCodeScanLogisticRequest {
	private List<String> qrCodes;
	private long operatorId;
	private LogisticOperatorType operatorType;
	private long receiverId;
	private long orderId;
	private OrderType orderType;
	private MovementType movementType;

	public List<String> getQrCodes() {
		return qrCodes;
	}

	public void setQrCodes(List<String> qrCodes) {
		this.qrCodes = qrCodes;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public LogisticOperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(LogisticOperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

}

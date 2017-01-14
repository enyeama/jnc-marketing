/**
 * 
 */
package com.sap.jnc.marketing.dto.request.logistic;

import com.sap.jnc.marketing.persistence.enumeration.LogisticOperatorType;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.enumeration.OrderType;

/**
 * @author Quansheng Liu I075496
 */
public class LogisticRequest {
	private MovementType movementType;
	private LogisticOperatorType operatorType;
	private long operatorId;
	private String qrCode;
	private OrderType orderType;
	private long orderId;

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

	public LogisticOperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(LogisticOperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}

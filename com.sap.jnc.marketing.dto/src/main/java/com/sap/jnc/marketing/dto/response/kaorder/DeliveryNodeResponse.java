/**
 * 
 */
package com.sap.jnc.marketing.dto.response.kaorder;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author Quansheng Liu I075496
 */
public class DeliveryNodeResponse implements Serializable {

	private static final long serialVersionUID = -19317733101723575L;

	private Long employeeId;
	private String employeeExternalId;
	private String employeeName;
	private Long positionId;
	private String positionExternalId;
	private String positionName;
	private Long dealerId;
	private String dealerName;

	public DeliveryNodeResponse() {

	}

	public DeliveryNodeResponse(PositionView positionView) {
		if (positionView == null) {
			return;
		}
		this.setPositionId(positionView.getId());
		this.setPositionName(positionView.getName());
		this.setPositionExternalId(positionView.getExternalId());
		EmployeeView employee = positionView.getEmployee();
		if (employee != null) {
			this.setEmployeeId(employee.getId());
			this.setEmployeeName(employee.getName());
			this.setEmployeeExternalId(employee.getExternalId());
		}
	}

	public DeliveryNodeResponse(Dealer dealer) {
		if (dealer == null) {
			return;
		}
		this.setDealerId(dealer.getId());
		this.setDealerName(dealer.getName());
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getEmployeeExternalId() {
		return employeeExternalId;
	}

	public void setEmployeeExternalId(String employeeExternalId) {
		this.employeeExternalId = employeeExternalId;
	}

	public String getPositionExternalId() {
		return positionExternalId;
	}

	public void setPositionExternalId(String positionExternalId) {
		this.positionExternalId = positionExternalId;
	}

}

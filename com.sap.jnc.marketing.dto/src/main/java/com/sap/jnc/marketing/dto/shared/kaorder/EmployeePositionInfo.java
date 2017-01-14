/**
 * 
 */
package com.sap.jnc.marketing.dto.shared.kaorder;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author Quansheng Liu I075496
 */
public class EmployeePositionInfo implements Serializable {

	private static final long serialVersionUID = -2784826639381713511L;
	private Long employeeId;
	private String employeeExternalId;
	private String employeeName;
	private Long positionId;
	private String positionExternalId;
	private String positionName;

	public EmployeePositionInfo() {

	}

	public EmployeePositionInfo(PositionView positionView) {
		if (positionView == null) {
			return;
		}
		this.setPositionExternalId(positionView.getExternalId());
		this.setPositionId(positionView.getId());
		this.setPositionName(positionView.getName());
		EmployeeView employeeView = positionView.getEmployee();
		if (employeeView != null) {
			this.setEmployeeExternalId(employeeView.getExternalId());
			this.setEmployeeId(employeeView.getId());
			this.setEmployeeName(employeeView.getName());
		}
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeExternalId() {
		return employeeExternalId;
	}

	public void setEmployeeExternalId(String employeeExternalId) {
		this.employeeExternalId = employeeExternalId;
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

	public String getPositionExternalId() {
		return positionExternalId;
	}

	public void setPositionExternalId(String positionExternalId) {
		this.positionExternalId = positionExternalId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

}

package com.sap.jnc.marketing.dto.response.authentication;

import java.io.Serializable;

import com.sap.jnc.marketing.dto.shared.ValidityPeriodInfo;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;

/**
 * @author I071053 Diouf Du
 */
public class EmployeeReferenceResponse implements Serializable {

	private static final long serialVersionUID = 8320411767016195119L;

	private Long id;

	private String externalId;

	private String name;

	private String phone;

	private String idCardNO;

	private ValidityPeriodInfo validityPeriod;

	public EmployeeReferenceResponse() {
	}

	public EmployeeReferenceResponse(Employee employee) {
		if (employee == null) {
			return;
		}
		this.setId(employee.getId());
		this.setExternalId(employee.getExternalId());
		this.setName(employee.getName());
		this.setPhone(employee.getPhone());
		this.setIdCardNO(employee.getIdCardNO());
		this.setValidityPeriod(new ValidityPeriodInfo(employee.getValidityPeriod()));
	}

	public EmployeeReferenceResponse(EmployeeView employeeView) {
		if (employeeView == null) {
			return;
		}
		this.setId(employeeView.getId());
		this.setExternalId(employeeView.getExternalId());
		this.setName(employeeView.getName());
		this.setPhone(employeeView.getPhone());
		this.setIdCardNO(employeeView.getIdCardNO());
		this.setValidityPeriod(new ValidityPeriodInfo(employeeView.getValidityPeriod()));
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCardNO() {
		return this.idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}

	public ValidityPeriodInfo getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriodInfo validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
}

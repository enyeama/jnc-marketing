package com.sap.jnc.marketing.dto.shared.kaorder;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;

/**
 * @author Joel.Cheng I310645
 */
public class EmployeeInfo implements Serializable {

	private static final long serialVersionUID = 4448221262260184439L;

	private Long id;

	private String externalId;

	private String name;

	private String phone;

	private String idCardNO;

	public EmployeeInfo() {
	}

	public EmployeeInfo(Employee employee) {
		this.id = employee.getId();
		this.externalId = employee.getExternalId();
		this.name = employee.getName();
		this.phone = employee.getPhone();
		this.idCardNO = employee.getIdCardNO();
	}

	public EmployeeInfo(EmployeeView employee) {
		this.id = employee.getId();
		this.externalId = employee.getExternalId();
		this.name = employee.getName();
		this.phone = employee.getPhone();
		this.idCardNO = employee.getIdCardNO();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCardNO() {
		return idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}

}

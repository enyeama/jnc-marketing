package com.sap.jnc.marketing.dto.request.register;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 员工注册请求
 * 
 * @author I323560
 *
 */
public class EmployeeRegisterRequest {

	@NotBlank
	private String employeeName;

	@NotBlank
	private String employeePhone;

	@NotBlank
	private String verificationCode;

	@NotBlank
	private String employeeNumber;

	@NotBlank
	private String idNumber;

	@NotBlank
	private String openId;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "EmployeeRegisterRequest [employeeName=" + employeeName + ", employeePhone=" + employeePhone
				+ ", verificationCode=" + verificationCode + ", employeeNumber=" + employeeNumber + ", idNumber="
				+ idNumber + ", openId=" + openId + "]";
	}

}

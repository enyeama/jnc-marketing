package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable

public class LoginAccount implements Serializable {

	private static final long serialVersionUID = -8779325115445356278L;

	@Column(name = "loginAccountUserName")
	private String userName;

	@Column(name = "loginAccountUserPassword")
	private String password;
	
	@Column(name = "externalEmployeeId")
	private String externalEmployeeId;

	@Column(name = "mobile", unique = true)
	private String mobile;

	@Column(name = "IdCardNumber")
	private String IdCardNumber;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExternalEmployeeId() {
		return externalEmployeeId;
	}

	public void setExternalEmployeeId(String externalEmployeeId) {
		this.externalEmployeeId = externalEmployeeId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCardNumber() {
		return IdCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		IdCardNumber = idCardNumber;
	}
}

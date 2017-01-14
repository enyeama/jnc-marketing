package com.sap.jnc.marketing.dto.request.register;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by guokai on 16/6/28.
 */
public class RegisterRequest {

	@NotBlank
	private String userName;

	@NotBlank
	private String phoneNumber;

	private String jobNUmber;

	private String IDcard;

	@NotBlank
	private String code;

	private Integer type;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIDcard() {
		return IDcard;
	}

	public void setIDcard(String IDcard) {
		this.IDcard = IDcard;
	}

	public String getJobNUmber() {
		return jobNUmber;
	}

	public void setJobNUmber(String jobNUmber) {
		this.jobNUmber = jobNUmber;
	}
}

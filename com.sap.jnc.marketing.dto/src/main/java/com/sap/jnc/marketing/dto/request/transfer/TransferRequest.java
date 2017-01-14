package com.sap.jnc.marketing.dto.request.transfer;

/**
 * Created by dy on 16/6/14.
 */
public class TransferRequest {

	private String userOpenId;

	private String clientIp;

	private String realUserName;

	private String description;

	private String verifyCode;

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getRealUserName() {
		return realUserName;
	}

	public void setRealUserName(String realUserName) {
		this.realUserName = realUserName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
}

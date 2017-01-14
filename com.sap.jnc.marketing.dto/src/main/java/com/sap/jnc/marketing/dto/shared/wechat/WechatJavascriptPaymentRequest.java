package com.sap.jnc.marketing.dto.shared.wechat;

public class WechatJavascriptPaymentRequest {

	private String timestamp;

	private String nonceStr;

	private String prepayId;

	private String signType;

	private String paySign;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	@Override
	public String toString() {
		return "WechatJavascriptPaymentRequest{" + "timestamp='" + timestamp + '\'' + ", nonceStr='" + nonceStr + '\'' + ", prepayId='" + prepayId
			+ '\'' + ", signType='" + signType + '\'' + ", paySign='" + paySign + '\'' + '}';
	}
}

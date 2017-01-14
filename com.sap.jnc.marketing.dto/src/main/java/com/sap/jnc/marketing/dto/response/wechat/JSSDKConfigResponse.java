package com.sap.jnc.marketing.dto.response.wechat;

/**
 * Created by guokai on 16/6/16.
 */
public class JSSDKConfigResponse {

	private String appId;

	private String timestamp;

	private String nonceStr;

	private String signature;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "JSSDKConfig{" + "appId='" + appId + '\'' + ", timestamp='" + timestamp + '\'' + ", nonceStr='" + nonceStr + '\'' + ", signature='"
			+ signature + '\'' + '}';
	}
}

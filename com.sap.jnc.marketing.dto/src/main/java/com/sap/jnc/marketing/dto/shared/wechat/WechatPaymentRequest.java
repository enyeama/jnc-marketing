package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("WechatPaymentRequest")
public class WechatPaymentRequest {

	@XStreamAlias("appid")
	private String appid;

	@XStreamAlias("mch_id")
	private String mchId;

	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	@XStreamAlias("nonce_str")
	private String nonceStr;

	@XStreamAlias("sign")
	private String sign;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "WechatPaymentRequest{" +
				"appid='" + appid + '\'' +
				", mchId='" + mchId + '\'' +
				", outTradeNo='" + outTradeNo + '\'' +
				", nonceStr='" + nonceStr + '\'' +
				", sign='" + sign + '\'' +
				'}';
	}
}

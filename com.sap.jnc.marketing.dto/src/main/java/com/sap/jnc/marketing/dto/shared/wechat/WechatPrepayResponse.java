package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Date;

@XStreamAlias("WechatPrepayResponse")
public class WechatPrepayResponse {

	@XStreamAlias("return_code")
	private String returnCode;

	@XStreamAlias("return_msg")
	private String returnMsg;

	@XStreamAlias("appid")
	private String appid;

	@XStreamAlias("mch_id")
	private String mchId;

	@XStreamAlias("device_info")
	private String deviceInfo;

	@XStreamAlias("nonce_str")
	private String nonceStr;

	@XStreamAlias("sign")
	private String sign;

	@XStreamAlias("result_code")
	private String resultCode;

	@XStreamAlias("err_code")
	private String errCode;

	@XStreamAlias("err_code_des")
	private String errCodeDes;

	@XStreamAlias("trade_type")
	private String tradeType;

	@XStreamAlias("prepay_id")
	private String prepayId;

	@XStreamAlias("code_url")
	private String codeUrl;

	@XStreamAlias("creatAt")
	private Date creatAt;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

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

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
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

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public Date getCreatAt() {
		return creatAt;
	}

	public void setCreatAt(Date creatAt) {
		this.creatAt = creatAt;
	}

	@Override
	public String toString() {
		return "WechatPrepayResponse{" +
				"returnCode='" + returnCode + '\'' +
				", returnMsg='" + returnMsg + '\'' +
				", appid='" + appid + '\'' +
				", mchId='" + mchId + '\'' +
				", deviceInfo='" + deviceInfo + '\'' +
				", nonceStr='" + nonceStr + '\'' +
				", sign='" + sign + '\'' +
				", resultCode='" + resultCode + '\'' +
				", errCode='" + errCode + '\'' +
				", errCodeDes='" + errCodeDes + '\'' +
				", tradeType='" + tradeType + '\'' +
				", prepayId='" + prepayId + '\'' +
				", codeUrl='" + codeUrl + '\'' +
				", creatAt=" + creatAt +
				'}';
	}
}

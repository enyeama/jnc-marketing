package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("WechatPrepayRequest")
public class WechatPrepayRequest {

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

	@XStreamAlias("body")
	private String body;

	@XStreamAlias("detail")
	private String detail;

	@XStreamAlias("attach")
	private String attach;

	@XStreamAlias("out_trade_no")
	private String outTradeNo;

	@XStreamAlias("fee_type")
	private String feeType;

	@XStreamAlias("total_fee")
	private String totalFee;

	@XStreamAlias("spbill_create_ip")
	private String spbillCreateIp;

	@XStreamAlias("goods_tag")
	private String goodsTag;

	@XStreamAlias("notify_url")
	private String notifyUrl;

	@XStreamAlias("trade_type")
	private String tradeType;

	@XStreamAlias("product_id")
	private String productId;

	@XStreamAlias("limit_pay")
	private String limitPay;

	@XStreamAlias("openid")
	private String openid;

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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Override
	public String toString() {
		return "WechatPrepayRequest{" +
				"appid='" + appid + '\'' +
				", mchId='" + mchId + '\'' +
				", deviceInfo='" + deviceInfo + '\'' +
				", nonceStr='" + nonceStr + '\'' +
				", sign='" + sign + '\'' +
				", body='" + body + '\'' +
				", detail='" + detail + '\'' +
				", attach='" + attach + '\'' +
				", outTradeNo='" + outTradeNo + '\'' +
				", feeType='" + feeType + '\'' +
				", totalFee='" + totalFee + '\'' +
				", spbillCreateIp='" + spbillCreateIp + '\'' +
				", goodsTag='" + goodsTag + '\'' +
				", notifyUrl='" + notifyUrl + '\'' +
				", tradeType='" + tradeType + '\'' +
				", productId='" + productId + '\'' +
				", limitPay='" + limitPay + '\'' +
				", openid='" + openid + '\'' +
				'}';
	}
}

package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by dy on 16/5/31.
 */
@XStreamAlias("WechatTransferRequest")
public class WechatTransferRequest {

	@XStreamAlias("mch_appid")
	private String mchAppid;

	@XStreamAlias("mchid")
	private String mchid;

	@XStreamAlias("nonce_str")
	private String nonceStr;

	@XStreamAlias("sign")
	private String sign;

	@XStreamAlias("partner_trade_no")
	private String partnerTradeNo;

	@XStreamAlias("openid")
	private String openid;

	@XStreamAlias("check_name")
	private String checkName;

	@XStreamAlias("re_user_name")
	private String reUserName;

	@XStreamAlias("amount")
	private int amount;

	@XStreamAlias("desc")
	private String desc;

	@XStreamAlias("spbill_create_ip")
	private String spbillCreateIp;

	public String getMchAppid() {
		return mchAppid;
	}

	public void setMchAppid(String mchAppid) {
		this.mchAppid = mchAppid;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
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

	public String getPartnerTradeNo() {
		return partnerTradeNo;
	}

	public void setPartnerTradeNo(String partnerTradeNo) {
		this.partnerTradeNo = partnerTradeNo;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getReUserName() {
		return reUserName;
	}

	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	@Override
	public String toString() {
		return "WechatTransferRequest{" +
				"mchAppid='" + mchAppid + '\'' +
				", mchid='" + mchid + '\'' +
				", nonceStr='" + nonceStr + '\'' +
				", sign='" + sign + '\'' +
				", partnerTradeNo='" + partnerTradeNo + '\'' +
				", openid='" + openid + '\'' +
				", checkName='" + checkName + '\'' +
				", reUserName='" + reUserName + '\'' +
				", amount=" + amount +
				", desc='" + desc + '\'' +
				", spbillCreateIp='" + spbillCreateIp + '\'' +
				'}';
	}
}

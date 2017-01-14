package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by dy on 16/6/27.
 */
@XStreamAlias("wechatBonusQueryRequest")
public class WechatBonusQueryRequest {

    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("mch_billno")
    private String mchBillno;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("nonce_str")
    private String appid;

    @XStreamAlias("bill_type")
    private String billType;

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

    public String getMchBillno() {
        return mchBillno;
    }

    public void setMchBillno(String mchBillno) {
        this.mchBillno = mchBillno;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    @Override
    public String toString() {
        return "WechatBonusQueryRequest{" +
                "nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", mchBillno='" + mchBillno + '\'' +
                ", mchId='" + mchId + '\'' +
                ", appid='" + appid + '\'' +
                ", billType='" + billType + '\'' +
                '}';
    }
}

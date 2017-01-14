package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by dy on 16/6/24.
 */
@XStreamAlias("WechatBonusRequest")
public class WechatBonusRequest {

    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("mch_billno")
    private String mchBillno;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("wxappid")
    private String wxappid;

    @XStreamAlias("send_name")
    private String sendName;

    @XStreamAlias("re_openid")
    private String reOpenid;

    @XStreamAlias("total_amount")
    private Integer totalAmount;

    @XStreamAlias("total_num")
    private Integer totalNum;

    @XStreamAlias("wishing")
    private String wishing;

    @XStreamAlias("client_ip")
    private String clientIp;

    @XStreamAlias("act_name")
    private String actName;

    @XStreamAlias("remark")
    private String remark;

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

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getReOpenid() {
        return reOpenid;
    }

    public void setReOpenid(String reOpenid) {
        this.reOpenid = reOpenid;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WechatBonusRequest{" +
                "nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", mchBillno='" + mchBillno + '\'' +
                ", mchId='" + mchId + '\'' +
                ", wxappid='" + wxappid + '\'' +
                ", sendName='" + sendName + '\'' +
                ", reOpenid='" + reOpenid + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalNum=" + totalNum +
                ", wishing='" + wishing + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", actName='" + actName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

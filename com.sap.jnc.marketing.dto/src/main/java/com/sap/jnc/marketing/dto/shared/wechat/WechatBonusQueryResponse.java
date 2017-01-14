package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by dy on 16/6/27.
 */
@XStreamAlias("wechatBonusQueryResponse")
public class WechatBonusQueryResponse {

    @XStreamAlias("return_code")
    private String returnCode;

    @XStreamAlias("return_msg")
    private String returnMsg;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("mch_billno")
    private String mchBillno;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("detail_id")
    private String detailId;

    @XStreamAlias("status")
    private String status;

    @XStreamAlias("send_type")
    private String sendType;

    @XStreamAlias("totalAmount")
    private String totalAmount;

    @XStreamAlias("open_id")
    private String openid;

    @XStreamAlias("rcv_time")
    private String rcvTime;

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

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRcvTime() {
        return rcvTime;
    }

    public void setRcvTime(String rcvTime) {
        this.rcvTime = rcvTime;
    }

    @Override
    public String toString() {
        return "WechatBonusQueryResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", sign='" + sign + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", mchBillno='" + mchBillno + '\'' +
                ", mchId='" + mchId + '\'' +
                ", detailId='" + detailId + '\'' +
                ", status='" + status + '\'' +
                ", sendType='" + sendType + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", openid='" + openid + '\'' +
                ", rcvTime='" + rcvTime + '\'' +
                '}';
    }
}

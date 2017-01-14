package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by dy on 16/6/24.
 */
@XStreamAlias("WechatBonusResponse")
public class WechatBonusResponse {

    @XStreamAlias("return_code")
    private String returnCode;

    @XStreamAlias("return_msg")
    private String returnMsg;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("err_code")
    private String errCode;

    @XStreamAlias("err_code_des")
    private String errCodeDes;

    @XStreamAlias("mch_billno")
    private String mchBillno;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("wxappid")
    private String wxappid;

    @XStreamAlias("re_openid")
    private String reOpenid;

    @XStreamAlias("total_amount")
    private String totalAmount;

    @XStreamAlias("send_listid")
    private String sendListid;

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

    public String getReOpenid() {
        return reOpenid;
    }

    public void setReOpenid(String reOpenid) {
        this.reOpenid = reOpenid;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSendListid() {
        return sendListid;
    }

    public void setSendListid(String sendListid) {
        this.sendListid = sendListid;
    }

    @Override
    public String toString() {
        return "WechatBonusResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", sign='" + sign + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errCodeDes='" + errCodeDes + '\'' +
                ", mchBillno='" + mchBillno + '\'' +
                ", mchId='" + mchId + '\'' +
                ", wxappid='" + wxappid + '\'' +
                ", reOpenid='" + reOpenid + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", sendListid='" + sendListid + '\'' +
                '}';
    }
}
